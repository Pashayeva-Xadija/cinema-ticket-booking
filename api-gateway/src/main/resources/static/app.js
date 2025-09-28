const API = window.location.origin; // Gateway domeni
document.getElementById('apiBase').textContent = API;

let accessToken = null;
let currentShowtimeId = null;
let currentBookingId = null;

const log = (...a) => {
    const el = document.getElementById('log');
    el.textContent = [el.textContent, a.map(x => typeof x==='string'?x:JSON.stringify(x, null, 2)).join(' ')].filter(Boolean).join('\n');
    const short = (accessToken || '').slice(0,12) + (accessToken? '…':'');
    document.getElementById('tokenShort').textContent = short || '-';
};

async function req(path, opt={}) {
    const headers = opt.headers || {};
    if (accessToken) headers['Authorization'] = 'Bearer ' + accessToken;
    headers['Content-Type'] = headers['Content-Type'] || 'application/json';
    const r = await fetch(API + path, { ...opt, headers });
    const txt = await r.text();
    let body; try { body = JSON.parse(txt); } catch { body = txt; }
    if (!r.ok) throw new Error(`${r.status} ${r.statusText}: ${txt}`);
    return body;
}


async function register() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    try {
        const res = await req('/api/auth/auth/register', { method:'POST', body: JSON.stringify({ email, password }) });
        log('register ok', res);
    } catch(e){ log('register err', e.message); }
}
async function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    try {
        const res = await req('/api/auth/auth/login', { method:'POST', body: JSON.stringify({ email, password }) });
        accessToken = res?.accessToken || res?.token || null;
        log('login ok', res);
    } catch(e){ log('login err', e.message); }
}
function logout(){ accessToken = null; log('logged out'); }

async function loadMovies() {
    try {
        const data = await req('/api/showtime/movies');
        const sel = document.getElementById('movie'); sel.innerHTML = '';
        (data||[]).forEach(x => { const o=document.createElement('option'); o.value=x.id; o.textContent=x.title; sel.appendChild(o); });
        log('movies', data);
    } catch(e){ log('movies err', e.message); }
}
async function loadShowtimes() {
    try {
        const movieId = document.getElementById('movie').value;
        const data = await req(`/api/showtime/${movieId}/showtimes`);
        const sel = document.getElementById('showtime'); sel.innerHTML='';
        (data||[]).forEach(x => { const o=document.createElement('option'); o.value=x.id; o.textContent=`${x.hall} • ${x.startTime}`; sel.appendChild(o); });
        log('showtimes', data);
    } catch(e){ log('showtimes err', e.message); }
}

async function loadSeats() {
    try {
        currentShowtimeId = document.getElementById('showtime').value;
        const data = await req(`/api/inventory/seats?showtimeId=${currentShowtimeId}`);
        log('seats', data);
    } catch(e){ log('seats err', e.message); }
}
async function reserve() {
    try {
        const seat = document.getElementById('seat').value;
        const data = await req('/api/inventory/reserve', { method:'POST', body: JSON.stringify({ showtimeId: currentShowtimeId, seat }) });
        log('reserve ok', data);
    } catch(e){ log('reserve err', e.message); }
}


async function checkout() {
    try {
        const data = await req('/api/booking/checkout', { method:'POST', body: JSON.stringify({ showtimeId: currentShowtimeId }) });
        currentBookingId = data?.id;
        log('checkout ok', data);
    } catch(e){ log('checkout err', e.message); }
}
async function pay() {
    try {
        const data = await req('/api/payment/pay', { method:'POST', body: JSON.stringify({ bookingId: currentBookingId }) });
        log('pay ok', data);
    } catch(e){ log('pay err', e.message); }
}
