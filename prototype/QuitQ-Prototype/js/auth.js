
// auth.js  —  Login, Register, Logout, Role selection


function switchTab(tab) {
  document.getElementById('login-form').style.display = tab === 'login'    ? 'block' : 'none';
  document.getElementById('reg-form').style.display   = tab === 'register' ? 'block' : 'none';
  document.getElementById('tab-login').classList.toggle('active', tab === 'login');
  document.getElementById('tab-reg').classList.toggle('active',   tab === 'register');
}

function pickRole(role) {
  selectedRole = role;
  var roles = ['user', 'seller', 'admin'];
  for (var i = 0; i < roles.length; i++) {
    document.getElementById('role-' + roles[i]).classList.toggle('active', roles[i] === role);
  }
}

function doLogin() {
  var email = document.getElementById('l-email').value.trim();
  var pass  = document.getElementById('l-pass').value.trim();
  var err   = document.getElementById('login-err');

  if (!email || !pass) {
    err.style.display = 'block';
    return;
  }
  err.style.display = 'none';

  currentUser = { name: email.split('@')[0], email: email };
  currentRole = selectedRole;

  // Update navbar buttons
  document.getElementById('btn-login').style.display  = 'none';
  document.getElementById('btn-logout').style.display = '';
  document.getElementById('btn-dash').style.display   = (currentRole !== 'user') ? '' : 'none';

  // Redirect based on role
  if (currentRole === 'seller') {
    document.getElementById('seller-name').textContent = '— ' + currentUser.name;
    renderSellerProducts();
    gp('seller-page');
  } else if (currentRole === 'admin') {
    gp('admin-page');
    renderAdmin('overview');
  } else {
    goHome();
    showToast('Welcome back, ' + currentUser.name + '!');
  }
}

function doRegister() {
  var name  = document.getElementById('r-name').value.trim();
  var email = document.getElementById('r-email').value.trim();
  var pass  = document.getElementById('r-pass').value.trim();
  var err   = document.getElementById('reg-err');
  var suc   = document.getElementById('reg-suc');

  if (!name || !email || !pass) {
    err.style.display = 'block';
    suc.style.display = 'none';
    return;
  }
  err.style.display = 'none';
  suc.style.display = 'block';

  setTimeout(function() {
    suc.style.display = 'none';
    switchTab('login');
  }, 2000);
}

function doLogout() {
  currentUser = null;
  currentRole = 'user';
  document.getElementById('btn-login').style.display  = '';
  document.getElementById('btn-logout').style.display = 'none';
  document.getElementById('btn-dash').style.display   = 'none';
  goHome();
  showToast('Logged out successfully.');
}

function checkPwdStrength(val) {
  var score = 0;
  if (val.length >= 8)          score++;
  if (/[A-Z]/.test(val))        score++;
  if (/[0-9]/.test(val))        score++;
  if (/[^A-Za-z0-9]/.test(val)) score++;

  var colors = { weak:'#f44336', med:'#ff9800', strong:'#4caf50' };
  var cls    = score <= 1 ? 'weak' : score <= 3 ? 'med' : 'strong';
  var labels = ['', 'Weak', 'Fair', 'Good', 'Strong'];

  for (var i = 1; i <= 4; i++) {
    document.getElementById('sb' + i).style.background = (i <= score) ? colors[cls] : '#eee';
  }
  var txt = document.getElementById('pwd-txt');
  txt.textContent = score ? labels[score] : 'Password strength';
  txt.style.color = score <= 1 ? colors.weak : score <= 3 ? colors.med : colors.strong;
}

function goDash() {
  if (currentRole === 'seller') {
    renderSellerProducts();
    gp('seller-page');
  } else if (currentRole === 'admin') {
    gp('admin-page');
    renderAdmin('overview');
  }
}
