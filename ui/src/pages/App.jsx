import React, { useState, useEffect } from 'react';

const API = 'http://localhost:8080';

function useAuthHeader(user, pass) {
  if (!user || !pass) return {};
  return { 'Authorization': 'Basic ' + btoa(user + ':' + pass) };
}

export default function App() {
  const [page, setPage] = useState('login'); // login | register | order | admin
  const [user, setUser] = useState('');
  const [pass, setPass] = useState('');
  const [me, setMe] = useState(null);

  const [regUser, setRegUser] = useState('');
  const [regPass, setRegPass] = useState('');

  const [amount, setAmount] = useState(399.99);
  const [address, setAddress] = useState('221B Baker Street');
  const [orderResp, setOrderResp] = useState(null);

  const [pStatus, setPStatus] = useState(null);
  const [sStatus, setSStatus] = useState(null);

  const authHeaders = useAuthHeader(user, pass);

  const fetchWhoAmI = async () => {
    try {
      const r = await fetch(`${API}/auth/whoami`, { headers: authHeaders });
      if (r.ok) setMe(await r.json()); else setMe(null);
    } catch { setMe(null); }
  };

  useEffect(() => { if (user && pass) fetchWhoAmI(); }, [user, pass]);

  const doLogin = async (e) => {
    e.preventDefault();
    await fetchWhoAmI();
    if (me) setPage('order');
  };

  const doRegister = async (e) => {
    e.preventDefault();
    const r = await fetch(`${API}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type':'application/json' },
      body: JSON.stringify({ username: regUser, password: regPass })
    });
    if (r.ok) alert('Registered. Now login.'); else alert('Registration failed');
    setPage('login');
  };

  const placeOrder = async () => {
    const r = await fetch(`${API}/place-order`, {
      method: 'POST',
      headers: { 'Content-Type':'application/json', ...authHeaders },
      body: JSON.stringify({ amount: Number(amount), address })
    });
    setOrderResp(await r.json());
  };

  const getStatuses = async () => {
    const ph = await fetch(`${API}/admin/payment/status`, { headers: authHeaders });
    const sh = await fetch(`${API}/admin/shipping/status`, { headers: authHeaders });
    setPStatus(await ph.json());
    setSStatus(await sh.json());
  };

  const togglePayment = async (on) => {
    await fetch(`${API}/admin/payment/${on ? 'enable' : 'disable'}`, { method: 'POST', headers: authHeaders });
    getStatuses();
  };
  const toggleShipping = async (on) => {
    await fetch(`${API}/admin/shipping/${on ? 'enable' : 'disable'}`, { method: 'POST', headers: authHeaders });
    getStatuses();
  };

  const Login = () => (
    <form onSubmit={doLogin} style={{display:'grid', gap:8, maxWidth:320}}>
      <h2>Login</h2>
      <input placeholder="username" value={user} onChange={e=>setUser(e.target.value)} />
      <input placeholder="password" type="password" value={pass} onChange={e=>setPass(e.target.value)} />
      <button type="submit">Login</button>
      <button type="button" onClick={()=>setPage('register')}>Register</button>
      <div style={{marginTop:8, fontSize:12, opacity:.7}}>
        Admin creds: <code>admin / admin</code>
      </div>
    </form>
  );

  const Register = () => (
    <form onSubmit={doRegister} style={{display:'grid', gap:8, maxWidth:320}}>
      <h2>Register</h2>
      <input placeholder="new username" value={regUser} onChange={e=>setRegUser(e.target.value)} />
      <input placeholder="new password" type="password" value={regPass} onChange={e=>setRegPass(e.target.value)} />
      <button type="submit">Create account</button>
      <button type="button" onClick={()=>setPage('login')}>Back to login</button>
    </form>
  );

  const Order = () => (
    <div style={{display:'grid', gap:8, maxWidth:520}}>
      <h2>Place Order</h2>
      <label>Amount</label>
      <input type="number" step="0.01" value={amount} onChange={e=>setAmount(e.target.value)} />
      <label>Address</label>
      <input value={address} onChange={e=>setAddress(e.target.value)} />
      <button onClick={placeOrder}>Place Order</button>

      {orderResp && (
        <pre style={{background:'#111', color:'#0f0', padding:10, overflow:'auto'}}>
{JSON.stringify(orderResp, null, 2)}
        </pre>
      )}

      <div style={{display:'flex', gap:8}}>
        <button onClick={()=>setPage('admin')}>Admin Dashboard</button>
        <button onClick={()=>{ setUser(''); setPass(''); setMe(null); setPage('login'); }}>Logout</button>
      </div>
    </div>
  );

  const Admin = () => (
    <div style={{display:'grid', gap:8, maxWidth:520}}>
      <h2>Admin Dashboard</h2>
      <div>
        <button onClick={getStatuses}>Refresh Status</button>
      </div>
      <div style={{display:'grid', gap:8, border:'1px solid #ccc', padding:10}}>
        <h3>Payment Service</h3>
        <div>Status: {pStatus ? String(pStatus.enabled) : 'unknown'}</div>
        <button onClick={()=>togglePayment(true)}>Enable</button>
        <button onClick={()=>togglePayment(false)}>Disable</button>
      </div>
      <div style={{display:'grid', gap:8, border:'1px solid #ccc', padding:10}}>
        <h3>Shipping Service</h3>
        <div>Status: {sStatus ? String(sStatus.enabled) : 'unknown'}</div>
        <button onClick={()=>toggleShipping(true)}>Enable</button>
        <button onClick={()=>toggleShipping(false)}>Disable</button>
      </div>
      <div style={{display:'flex', gap:8}}>
        <button onClick={()=>setPage('order')}>Back to Orders</button>
        <button onClick={()=>{ setUser(''); setPass(''); setMe(null); setPage('login'); }}>Logout</button>
      </div>
      <div style={{fontSize:12, opacity:.7}}>
        Note: Admin dashboard requires <b>admin/admin</b> (ROLE_ADMIN). Regular users cannot access /admin APIs.
      </div>
    </div>
  );

  const Header = () => (
    <div style={{display:'flex', justifyContent:'space-between', marginBottom:16}}>
      <div>
        <button onClick={()=>setPage('login')}>Login</button>
        <button onClick={()=>setPage('order')}>Order</button>
        <button onClick={()=>setPage('admin')}>Admin</button>
      </div>
      <div>{me ? `Logged in as ${me.username}` : 'Not logged in'}</div>
    </div>
  );

  return (
    <div style={{padding:20, fontFamily:'system-ui, sans-serif'}}>
      <Header/>
      {page === 'login' && <Login/>}
      {page === 'register' && <Register/>}
      {page === 'order' && <Order/>}
      {page === 'admin' && <Admin/>}
    </div>
  );
}
