package com.example.inventoryservice.serviceImpl;

import com.example.inventoryservice.dto.ConfirmHoldRequest;
import com.example.inventoryservice.dto.HoldRequest;
import com.example.inventoryservice.dto.HoldResponse;
import com.example.inventoryservice.enumtype.SeatState;
import com.example.inventoryservice.exception.ConflictException;
import com.example.inventoryservice.exception.NotFoundException;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.model.Hold;
import com.example.inventoryservice.model.SeatStatus;
import com.example.inventoryservice.repository.HoldRepository;
import com.example.inventoryservice.repository.SeatStatusRepository;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.service.SeatCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final SeatStatusRepository seatRepo;
    private final HoldRepository holdRepo;
    private final InventoryMapper mapper;

    private final SeatCacheService seatCacheService;

    @Override
    @Transactional
    public HoldResponse holdSeats(HoldRequest request) {
        List<Integer> seats = request.getSeatNumbers();
        ensureFreeSeats(request.getShowtimeId(), seats);

        seats.forEach(seat -> {
            markSeat(request.getShowtimeId(), seat, SeatState.HELD);
            seatCacheService.setSeatStatus(request.getShowtimeId(), seat.toString(), "HELD");
        });

        Hold hold = Hold.builder()
                .showtimeId(request.getShowtimeId())
                .userId(request.getUserId())
                .seatNumbers(seats)
                .createdAt(Instant.now())
                .build();

        return mapper.toResponse(holdRepo.save(hold));
    }

    private void ensureFreeSeats(Long showtimeId, List<Integer> seats) {
        for (Integer seat : seats) {
            String cached = seatCacheService.getSeatStatus(showtimeId, seat.toString());
            if (cached != null && !"FREE".equals(cached)) {
                throw new ConflictException("Seat " + seat + " is not FREE (Redis)");
            }
            SeatStatus s = seatRepo.findByShowtimeIdAndSeatNumber(showtimeId, seat)
                    .orElse(SeatStatus.builder()
                            .showtimeId(showtimeId)
                            .seatNumber(seat)
                            .state(SeatState.FREE)
                            .build());
            if (s.getState() != SeatState.FREE) {
                throw new ConflictException("Seat " + seat + " is not FREE");
            }
        }
    }

    private void markSeat(Long showtimeId, Integer seat, SeatState state) {
        SeatStatus s = seatRepo.findByShowtimeIdAndSeatNumber(showtimeId, seat)
                .orElse(SeatStatus.builder()
                        .showtimeId(showtimeId)
                        .seatNumber(seat)
                        .state(SeatState.FREE)
                        .build());
        s.setState(state);
        seatRepo.save(s);
    }

    @Override
    @Transactional
    public HoldResponse confirmHold(ConfirmHoldRequest request) {
        Hold hold = holdRepo.findById(request.getHoldId())
                .orElseThrow(() -> new NotFoundException("Hold not found"));

        if (hold.getReleasedAt() != null) {
            throw new ConflictException("Hold already released");
        }
        if (hold.getConfirmedAt() != null) {
            return mapper.toResponse(hold);
        }

        for (Integer seat : hold.getSeatNumbers()) {
            SeatStatus s = seatRepo.findByShowtimeIdAndSeatNumber(hold.getShowtimeId(), seat)
                    .orElseThrow(() -> new NotFoundException("Seat not found: " + seat));
            if (s.getState() != SeatState.HELD) {
                throw new ConflictException("Seat " + seat + " is not HELD");
            }
            s.setState(SeatState.SOLD);
            seatRepo.save(s);

            seatCacheService.setSeatStatus(hold.getShowtimeId(), seat.toString(), "SOLD"); // 🔹 Redis
        }
        hold.setConfirmedAt(Instant.now());
        return mapper.toResponse(holdRepo.save(hold));
    }

    @Override
    @Transactional
    public void releaseHold(Long holdId) {
        Hold hold = holdRepo.findById(holdId)
                .orElseThrow(() ->new NotFoundException("Hold not found"));
        if (hold.getConfirmedAt() != null) {
            throw new ConflictException("Hold already confirmed");
        }
        if (hold.getReleasedAt() != null) return;

        for (Integer seat : hold.getSeatNumbers()) {
            SeatStatus s = seatRepo.findByShowtimeIdAndSeatNumber(hold.getShowtimeId(), seat)
                    .orElseThrow(() -> new NotFoundException("Seat not found: " + seat));
            if (s.getState() == SeatState.HELD) {
                s.setState(SeatState.FREE);
                seatRepo.save(s);

                seatCacheService.setSeatStatus(hold.getShowtimeId(), seat.toString(), "FREE"); // 🔹 Redis
            }
        }
        hold.setReleasedAt(Instant.now());
        holdRepo.save(hold);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatStatus> getSeats(Long showtimeId, SeatState state) {
        if (state == null) return seatRepo.findByShowtimeId(showtimeId);
        return seatRepo.findByShowtimeIdAndState(showtimeId, state);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isSeatAvailable(Long showtimeId, Integer seatNumber) {
        String cached = seatCacheService.getSeatStatus(showtimeId, seatNumber.toString());
        if (cached != null) {
            return "FREE".equals(cached);
        }

        return seatRepo.findByShowtimeIdAndSeatNumber(showtimeId, seatNumber)
                .map(s -> s.getState() == SeatState.FREE)
                .orElse(true);
    }
}
