
// products.js  —  Render, filter, sort and open products


function renderProducts() {
  var grid = document.getElementById('pgrid');

  if (!filteredProducts.length) {
    grid.innerHTML =
      '<div class="empty-state">' +
        '<div class="empty-icon">🔍</div>' +
        '<h3>No products found</h3>' +
        '<p>Try a different search or category</p>' +
      '</div>';
    return;
  }

  var html = '';
  for (var i = 0; i < filteredProducts.length; i++) {
    var p = filteredProducts[i];
    var stars = '';
    for (var s = 0; s < 5; s++) {
      stars += (s < Math.floor(p.rating)) ? '★' : '☆';
    }
    var btn = p.stock === 0
      ? '<button class="add-to-cart-btn oos" disabled>Out of Stock</button>'
      : '<button class="add-to-cart-btn" onclick="event.stopPropagation();addToCart(' + p.id + ')">Add to Cart</button>';

    html +=
      '<div class="product-card" onclick="openDetail(' + p.id + ')">' +
        '<div class="product-emoji">' + p.emoji + '</div>' +
        '<div class="product-name">' + p.name + '</div>' +
        '<div class="product-seller">by ' + p.seller + '</div>' +
        '<div class="product-rating">' + stars + ' (' + p.rev.toLocaleString() + ')</div>' +
        '<div class="product-price">Rs ' + p.price.toLocaleString() +
          '<span class="product-orig">Rs ' + p.orig.toLocaleString() + '</span>' +
          '<span class="product-disc"> ' + p.disc + '% off</span>' +
        '</div>' +
        btn +
      '</div>';
  }
  grid.innerHTML = html;
}

function filterCat(el, cat) {
  currentCat = cat;
  var btns = document.querySelectorAll('.cat-btn');
  for (var i = 0; i < btns.length; i++) btns[i].classList.remove('active');
  el.classList.add('active');
  applyFilters();
  document.getElementById('prod-heading').textContent =
    cat === 'All' ? 'Featured Products' : cat + ' Products';
}

function applyFilters() {
  var q     = document.getElementById('search-input').value.toLowerCase();
  var brand = document.getElementById('bf').value;

  filteredProducts = [];
  for (var i = 0; i < allProducts.length; i++) {
    var p = allProducts[i];
    var matchCat   = (currentCat === 'All') || (p.cat === currentCat);
    var matchQuery = (p.name.toLowerCase().indexOf(q) !== -1) ||
                     (p.seller.toLowerCase().indexOf(q) !== -1);
    var matchBrand = (!brand) || (p.brand === brand);
    if (matchCat && matchQuery && matchBrand) filteredProducts.push(p);
  }
  renderProducts();
}

function sortProds(by) {
  if (by === 'asc')    filteredProducts.sort(function(a, b) { return a.price - b.price; });
  if (by === 'desc')   filteredProducts.sort(function(a, b) { return b.price - a.price; });
  if (by === 'rating') filteredProducts.sort(function(a, b) { return b.rating - a.rating; });
  renderProducts();
}

function openDetail(id) {
  var p = null;
  for (var i = 0; i < allProducts.length; i++) {
    if (allProducts[i].id === id) { p = allProducts[i]; break; }
  }
  if (!p) return;

  detQty = 1;
  var stars = '';
  for (var s = 0; s < 5; s++) stars += (s < Math.floor(p.rating)) ? '★' : '☆';

  var actionHtml = p.stock > 0
    ? '<div style="color:green;font-size:13px;margin-bottom:10px;">In Stock (' + p.stock + ' units)</div>' +
      '<div class="qty-row">' +
        '<button onclick="changeDetQty(-1)">-</button>' +
        '<span id="det-qty">1</span>' +
        '<button onclick="changeDetQty(1)">+</button>' +
      '</div>' +
      '<div class="detail-btns">' +
        '<button class="btn-add-cart" onclick="addToCartFromDetail(' + p.id + ')">ADD TO CART</button>' +
        '<button class="btn-buy-now"  onclick="addToCartFromDetail(' + p.id + ');gp(\'cart-page\');renderCart()">BUY NOW</button>' +
      '</div>'
    : '<div style="color:red;font-weight:bold;margin-top:8px;">Out of Stock</div>';

  document.getElementById('det').innerHTML =
    '<div class="detail-layout">' +
      '<div class="detail-emoji">' + p.emoji + '</div>' +
      '<div>' +
        '<div style="font-size:12px;color:#888;margin-bottom:5px;">' + p.cat + ' / ' + p.brand + '</div>' +
        '<div class="detail-name">' + p.name + '</div>' +
        '<div style="font-size:13px;color:#888;margin-bottom:7px;">Sold by ' + p.seller + '</div>' +
        '<div class="detail-rating">' + stars + ' ' + p.rating + ' (' + p.rev.toLocaleString() + ' reviews)</div>' +
        '<div class="detail-price">Rs ' + p.price.toLocaleString() +
          '<span class="orig">Rs ' + p.orig.toLocaleString() + '</span>' +
          '<span class="disc"> ' + p.disc + '% off</span>' +
        '</div>' +
        '<div class="detail-desc">' + p.desc + '</div>' +
        actionHtml +
        '<div class="detail-meta">Free Delivery &nbsp;|&nbsp; 10-day Returns &nbsp;|&nbsp; Secure Payment</div>' +
      '</div>' +
    '</div>';

  window._detProdId = id;
  gp('detail-page');
}

function changeDetQty(d) {
  detQty = Math.max(1, detQty + d);
  var el = document.getElementById('det-qty');
  if (el) el.textContent = detQty;
}

function addToCartFromDetail(id) {
  var p = null;
  for (var i = 0; i < allProducts.length; i++) {
    if (allProducts[i].id === id) { p = allProducts[i]; break; }
  }
  if (!p) return;

  var found = false;
  for (var j = 0; j < cart.length; j++) {
    if (cart[j].id === id) { cart[j].qty += detQty; found = true; break; }
  }
  if (!found) {
    var item = {};
    for (var k in p) item[k] = p[k];
    item.qty = detQty;
    cart.push(item);
  }
  updateCartCount();
  showToast(p.name + ' added to cart!');
}
