
// checkout.js  —  3-step checkout flow and order placement


function renderCheckout() {
  renderSteps();

  var sub   = cartTotal();
  var total = sub + (sub > 500 ? 0 : 99) - Math.floor(sub * 0.05);

  // Build order summary sidebar
  var summaryRows = '';
  for (var i = 0; i < cart.length; i++) {
    summaryRows +=
      '<div class="summary-row">' +
        '<span>' + cart[i].emoji + ' ' + cart[i].name + ' x' + cart[i].qty + '</span>' +
        '<span>Rs ' + (cart[i].price * cart[i].qty).toLocaleString() + '</span>' +
      '</div>';
  }
  var summaryHtml =
    '<div class="order-summary">' +
      '<h3>ORDER SUMMARY</h3>' +
      summaryRows +
      '<div class="summary-row total"><span>Total</span><span>Rs ' + total.toLocaleString() + '</span></div>' +
    '</div>';

  var formHtml = '';

  if (checkStep === 1) {
    formHtml =
      '<div class="form-section">' +
        '<h3>Shipping Address</h3>' +
        '<div class="form-row">' +
          '<div class="form-group"><label>First Name</label><input type="text" placeholder="John"></div>' +
          '<div class="form-group"><label>Last Name</label><input type="text" placeholder="Doe"></div>' +
        '</div>' +
        '<div class="form-group"><label>Address Line 1</label><input type="text" placeholder="123 MG Road, Koramangala"></div>' +
        '<div class="form-group"><label>Address Line 2</label><input type="text" placeholder="Apartment, suite, etc."></div>' +
        '<div class="form-row">' +
          '<div class="form-group"><label>City</label><input type="text" placeholder="Bengaluru"></div>' +
          '<div class="form-group"><label>PIN Code</label><input type="text" placeholder="560001"></div>' +
        '</div>' +
        '<div class="form-group">' +
          '<label>State</label>' +
          '<select>' +
            '<option>Karnataka</option><option>Maharashtra</option>' +
            '<option>Delhi</option><option>Tamil Nadu</option><option>Rajasthan</option>' +
          '</select>' +
        '</div>' +
        '<div class="btn-row">' +
          '<button class="btn-next" onclick="checkStep=2;renderCheckout()">Continue to Payment</button>' +
        '</div>' +
      '</div>';

  } else if (checkStep === 2) {
    formHtml =
      '<div class="form-section">' +
        '<h3>Payment Method</h3>' +
        '<div class="payment-option"><input type="radio" name="pay" checked> Credit / Debit Card</div>' +
        '<div class="payment-option"><input type="radio" name="pay"> UPI (GPay, PhonePe, Paytm)</div>' +
        '<div class="payment-option"><input type="radio" name="pay"> Net Banking</div>' +
        '<div class="payment-option"><input type="radio" name="pay"> Cash on Delivery</div>' +
        '<div style="background:#f9f9f9;border:1px solid #eee;border-radius:4px;padding:14px;margin-top:12px;">' +
          '<div class="form-group"><label>Card Number</label><input type="text" placeholder="1234 5678 9012 3456"></div>' +
          '<div class="form-row">' +
            '<div class="form-group"><label>Expiry (MM/YY)</label><input type="text" placeholder="MM / YY"></div>' +
            '<div class="form-group"><label>CVV</label><input type="text" placeholder="***"></div>' +
          '</div>' +
          '<div class="form-group"><label>Name on Card</label><input type="text" placeholder="John Doe"></div>' +
        '</div>' +
        '<div class="btn-row">' +
          '<button class="btn-back" onclick="checkStep=1;renderCheckout()">Back</button>' +
          '<button class="btn-next" onclick="checkStep=3;renderCheckout()">Review Order</button>' +
        '</div>' +
      '</div>';

  } else if (checkStep === 3) {
    formHtml =
      '<div class="form-section">' +
        '<h3>Review and Confirm</h3>' +
        '<div class="info-box">' +
          '<div class="info-label">Delivering to</div>' +
          '<div style="font-weight:bold;">John Doe</div>' +
          '<div style="font-size:13px;color:#555;">123 MG Road, Koramangala, Bengaluru 560001</div>' +
        '</div>' +
        '<div class="info-box">' +
          '<div class="info-label">Payment</div>' +
          '<div style="font-weight:bold;">Credit Card ending in ****3456</div>' +
        '</div>' +
        '<div style="font-size:12px;color:#888;margin-bottom:16px;line-height:1.6;">' +
          'By placing your order you agree to QuitQ Terms of Use and Privacy Policy.' +
        '</div>' +
        '<div class="btn-row">' +
          '<button class="btn-back" onclick="checkStep=2;renderCheckout()">Back</button>' +
          '<button class="btn-next" style="background:#fb641b;" onclick="placeOrder()">PLACE ORDER</button>' +
        '</div>' +
      '</div>';
  }

  document.getElementById('chk-body').innerHTML =
    '<div class="checkout-grid">' + formHtml + summaryHtml + '</div>';
}

function renderSteps() {
  var names = ['Shipping', 'Payment', 'Confirm'];
  var html  = '';
  for (var i = 0; i < names.length; i++) {
    var cls = (i + 1 === checkStep) ? 'active' : (i + 1 < checkStep ? 'done' : '');
    html +=
      '<div class="step ' + cls + '">' +
        '<div class="step-circle">' + (i + 1) + '</div>' +
        '<span class="step-label">' + names[i] + '</span>' +
      '</div>';
    if (i < 2) {
      html += '<div class="step-line' + (i + 1 < checkStep ? ' done' : '') + '"></div>';
    }
  }
  document.getElementById('steps-bar').innerHTML = html;
}

function placeOrder() {
  var first = cart.length ? cart[0] : { name:'Order', emoji:'📦', qty:1 };
  orders.unshift({
    id:     'ORD-00' + orderNum,
    name:   first.name,
    qty:    first.qty,
    price:  cartTotal(),
    status: 'Processing',
    date:   new Date().toISOString().split('T')[0],
    emoji:  first.emoji
  });
  document.getElementById('placed-order-id').textContent = '#ORD-00' + orderNum;
  orderNum++;
  cart = [];
  updateCartCount();
  showOverlay('order-success-overlay');
}
