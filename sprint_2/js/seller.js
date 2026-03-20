
// seller.js  —  Seller dashboard: product management


function renderSellerProducts() {
  document.getElementById('s-prod-count').textContent = sellerProducts.length;

  var html = '';
  for (var i = 0; i < sellerProducts.length; i++) {
    var p = sellerProducts[i];
    var stockColor = p.stock === 0 ? 'red' : p.stock < 5 ? 'orange' : 'green';
    var stockText  = p.stock === 0 ? 'Out of Stock' : p.stock + ' units';

    html +=
      '<tr>' +
        '<td>' + p.emoji + ' ' + p.name + '</td>' +
        '<td>Rs ' + p.price.toLocaleString() + '</td>' +
        '<td style="color:' + stockColor + ';">' + stockText + '</td>' +
        '<td>' +
          '<button onclick="removeSellerProduct(' + p.id + ')" ' +
            'style="background:none;border:none;color:red;cursor:pointer;font-size:12px;">' +
            'Remove' +
          '</button>' +
        '</td>' +
      '</tr>';
  }
  document.getElementById('s-prod-tbody').innerHTML = html;
}

function removeSellerProduct(id) {
  sellerProducts = sellerProducts.filter(function(p) { return p.id !== id; });
  allProducts    = allProducts.filter(function(p)    { return p.id !== id; });
  filteredProducts = allProducts.slice();
  renderSellerProducts();
  showToast('Product removed.');
}

function addProduct() {
  var name  = document.getElementById('np-name').value.trim();
  var price = parseInt(document.getElementById('np-price').value);
  var stock = parseInt(document.getElementById('np-stock').value);
  var cat   = document.getElementById('np-cat').value;

  if (!name || !price || !stock) {
    showToast('Please fill all required fields.');
    return;
  }

  var emojis = {
    Electronics: '💡',
    Fashion:     '👗',
    Mobile:      '📱',
    Home:        '🏠',
    Books:       '📚',
    Sports:      '🏃'
  };
  var emoji = emojis[cat] || '📦';
  var newId = Date.now();

  // Add to seller list
  sellerProducts.push({
    id:    newId,
    name:  name,
    cat:   cat,
    price: price,
    stock: stock,
    emoji: emoji
  });

  // Add to global product list
  allProducts.push({
    id:     newId,
    name:   name,
    cat:    cat,
    price:  price,
    orig:   Math.floor(price * 1.15),
    disc:   13,
    rating: 4.5,
    rev:    0,
    seller: currentUser ? currentUser.name : 'Seller',
    emoji:  emoji,
    stock:  stock,
    brand:  'Other',
    desc:   'New product listing.'
  });

  filteredProducts = allProducts.slice();

  hideOverlay('add-prod-overlay');
  renderSellerProducts();
  showToast('Product added successfully!');

  // Clear inputs
  document.getElementById('np-name').value  = '';
  document.getElementById('np-price').value = '';
  document.getElementById('np-stock').value = '';
}
