
// cart.js  —  Add, remove, update cart and render cart page


function addToCart(id) {
  var p = null;
  for (var i = 0; i < allProducts.length; i++) {
    if (allProducts[i].id === id) { p = allProducts[i]; break; }
  }
  if (!p) return;

  var found = false;
  for (var j = 0; j < cart.length; j++) {
    if (cart[j].id === id) { cart[j].qty++; found = true; break; }
  }
  if (!found) {
    var item = {};
    for (var k in p) item[k] = p[k];
    item.qty = 1;
    cart.push(item);
  }
  updateCartCount();
  showToast(p.name + ' added to cart!');
}

function removeFromCart(id) {
  cart = cart.filter(function(i) { return i.id !== id; });
  updateCartCount();
  renderCart();
}

function changeCartQty(id, d) {
  for (var i = 0; i < cart.length; i++) {
    if (cart[i].id === id) {
      cart[i].qty = Math.max(1, cart[i].qty + d);
      break;
    }
  }
  renderCart();
}

function cartTotal() {
  var total = 0;
  for (var i = 0; i < cart.length; i++) total += cart[i].price * cart[i].qty;
  return total;
}

function totalQty() {
  var qty = 0;
  for (var i = 0; i < cart.length; i++) qty += cart[i].qty;
  return qty;
}

function updateCartCount() {
  document.getElementById('cc').textContent = totalQty();
}

function renderCart() {
  var body = document.getElementById('cart-body');

  if (!cart.length) {
    body.innerHTML =
      '<div class="empty-state">' +
        '<div class="empty-icon">🛒</div>' +
        '<h3>Your cart is empty</h3>' +
        '<p>Add some products to get started</p>' +
        '<button onclick="goHome()">Browse Products</button>' +
      '</div>';
    return;
  }

  var sub   = cartTotal();
  var del   = sub > 500 ? 0 : 99;
  var disc  = Math.floor(sub * 0.05);
  var total = sub + del - disc;

  var itemsHtml = '';
  for (var i = 0; i < cart.length; i++) {
    var it = cart[i];
    itemsHtml +=
      '<div class="cart-item">' +
        '<div class="cart-emoji">' + it.emoji + '</div>' +
        '<div class="cart-info">' +
          '<div class="cart-name">' + it.name + '</div>' +
          '<div class="cart-seller">by ' + it.seller + '</div>' +
          '<div class="cart-price">Rs ' + (it.price * it.qty).toLocaleString() + '</div>' +
          '<div class="cart-controls">' +
            '<button onclick="changeCartQty(' + it.id + ',-1)">-</button>' +
            '<span>' + it.qty + '</span>' +
            '<button onclick="changeCartQty(' + it.id + ',1)">+</button>' +
            '<button class="remove-btn" onclick="removeFromCart(' + it.id + ')">Remove</button>' +
          '</div>' +
        '</div>' +
        '<div style="font-size:12px;color:#888;">Rs ' + it.price.toLocaleString() + ' each</div>' +
      '</div>';
  }

  var summaryHtml =
    '<div class="order-summary">' +
      '<h3>PRICE DETAILS</h3>' +
      '<div class="summary-row"><span>Price (' + totalQty() + ' items)</span><span>Rs ' + sub.toLocaleString() + '</span></div>' +
      '<div class="summary-row"><span>Delivery</span><span class="text-green">' + (del === 0 ? 'FREE' : 'Rs ' + del) + '</span></div>' +
      '<div class="summary-row"><span>Discount (5%)</span><span class="text-green">-Rs ' + disc.toLocaleString() + '</span></div>' +
      '<div class="summary-row total"><span>Total Amount</span><span>Rs ' + total.toLocaleString() + '</span></div>' +
      '<button class="checkout-btn" onclick="gp(\'checkout-page\');checkStep=1;renderCheckout()">PROCEED TO CHECKOUT</button>' +
    '</div>';

  body.innerHTML =
    '<div class="cart-layout">' +
      '<div>' + itemsHtml + '</div>' +
      summaryHtml +
    '</div>';
}
