
// app.js  —  Navigation, orders, toast, modals and init


// ---------- Page navigation ----------
function gp(id) {
  var pages = document.querySelectorAll('.page');
  for (var i = 0; i < pages.length; i++) pages[i].classList.remove('active');
  var target = document.getElementById(id);
  if (target) target.classList.add('active');
  window.scrollTo(0, 0);
}

function goHome() {
  gp('home-page');
  renderProducts();
}

// ---------- Overlay / Modal ----------
function showOverlay(id) {
  var el = document.getElementById(id);
  if (el) el.classList.add('show');
}

function hideOverlay(id) {
  var el = document.getElementById(id);
  if (el) el.classList.remove('show');
}

// Close overlay when clicking outside the modal box
window.addEventListener('click', function(e) {
  var overlays = document.querySelectorAll('.overlay');
  for (var i = 0; i < overlays.length; i++) {
    if (e.target === overlays[i]) overlays[i].classList.remove('show');
  }
});

// ---------- Toast ----------
function showToast(msg) {
  var t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  setTimeout(function() { t.classList.remove('show'); }, 3000);
}

// ---------- Orders ----------
function renderOrders() {
  var body = document.getElementById('orders-body');

  if (!orders.length) {
    body.innerHTML =
      '<div class="empty-state">' +
        '<div class="empty-icon">📦</div>' +
        '<h3>No orders yet</h3>' +
        '<p>Start shopping to see your orders here</p>' +
        '<button onclick="goHome()">Browse Products</button>' +
      '</div>';
    return;
  }

  var html = '';
  for (var i = 0; i < orders.length; i++) {
    var o = orders[i];
    html +=
      '<div class="order-card">' +
        '<div class="order-top">' +
          '<div>' +
            '<div class="order-id">#' + o.id + '</div>' +
            '<div class="order-date">' + o.date + '</div>' +
          '</div>' +
          '<span class="badge ' + o.status.toLowerCase() + '">' + o.status + '</span>' +
        '</div>' +
        '<div class="order-bottom">' +
          '<div class="order-emoji">' + o.emoji + '</div>' +
          '<div>' +
            '<div style="font-weight:500;">' + o.name + '</div>' +
            '<div style="font-size:13px;color:#888;">Qty: ' + o.qty + ' &nbsp;|&nbsp; Rs ' + o.price.toLocaleString() + '</div>' +
          '</div>' +
          '<button style="margin-left:auto;padding:6px 14px;background:white;border:1px solid #ddd;border-radius:4px;cursor:pointer;" ' +
            'onclick="showToast(\'Order tracking coming soon!\')">Track</button>' +
        '</div>' +
      '</div>';
  }
  body.innerHTML = html;
}

// ---------- Init ----------
window.onload = function() {
  renderProducts();
};
