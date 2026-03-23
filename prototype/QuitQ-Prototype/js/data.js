
// data.js  —  Global state and product / order data


var currentUser  = null;
var currentRole  = 'user';
var selectedRole = 'user';
var cart         = [];
var orderNum     = 5;
var checkStep    = 1;
var detQty       = 1;
var currentCat   = 'All';

var orders = [
  { id:'ORD-001', name:'iPhone 15 Pro', qty:1, price:89999, status:'Delivered',  date:'2026-03-10', emoji:'📱' },
  { id:'ORD-002', name:'Nike Air Max',  qty:2, price:8998,  status:'Shipped',    date:'2026-03-15', emoji:'👟' }
];

var sellerProducts = [
  { id:1, name:'iPhone 15 Pro',  cat:'Mobile',      price:89999,  stock:12, emoji:'📱' },
  { id:2, name:'MacBook Air M3', cat:'Electronics', price:114990, stock:5,  emoji:'💻' },
  { id:3, name:'AirPods Pro',    cat:'Electronics', price:24990,  stock:20, emoji:'🎧' },
  { id:4, name:'iPad Mini 6',    cat:'Electronics', price:46900,  stock:8,  emoji:'📱' }
];

var allProducts = [
  { id:1,  name:'iPhone 15 Pro',       cat:'Mobile',      price:89999,  orig:99999,  disc:10, rating:4.8, rev:2341, seller:'Apple Store India', emoji:'📱', stock:12,  brand:'Apple',   desc:'A17 Pro chip, titanium build, most powerful camera ever.' },
  { id:2,  name:'MacBook Air M3',      cat:'Electronics', price:114990, orig:129900, disc:11, rating:4.9, rev:876,  seller:'Apple Store India', emoji:'💻', stock:5,   brand:'Apple',   desc:'M3 chip, 18-hour battery life in a fanless design.' },
  { id:3,  name:'Samsung Galaxy S24',  cat:'Mobile',      price:74999,  orig:89999,  disc:17, rating:4.6, rev:1542, seller:'Samsung Official',  emoji:'📲', stock:18,  brand:'Samsung', desc:'Galaxy AI is here. 7 years of OS upgrades guaranteed.' },
  { id:4,  name:'Sony WH-1000XM5',     cat:'Electronics', price:24990,  orig:29990,  disc:17, rating:4.7, rev:3201, seller:'Sony India',         emoji:'🎧', stock:30,  brand:'Sony',    desc:'Best-in-class noise cancelling, 30-hour battery life.' },
  { id:5,  name:'Nike Air Max 270',    cat:'Fashion',     price:12995,  orig:14995,  disc:13, rating:4.5, rev:892,  seller:'Nike Official',      emoji:'👟', stock:0,   brand:'Nike',    desc:'Max Air heel cushioning. Lightweight breathable upper.' },
  { id:6,  name:'IKEA BILLY Bookcase', cat:'Home',        price:5490,   orig:6490,   disc:15, rating:4.4, rev:456,  seller:'IKEA India',         emoji:'🪑', stock:22,  brand:'IKEA',    desc:'Adjustable shelves. Sturdy and easy to assemble.' },
  { id:7,  name:'Canon EOS R50',       cat:'Electronics', price:64990,  orig:74990,  disc:13, rating:4.7, rev:312,  seller:'Canon India',        emoji:'📷', stock:7,   brand:'Canon',   desc:'24.2MP sensor, 4K video, lightweight mirrorless camera.' },
  { id:8,  name:'Adidas Ultraboost',   cat:'Sports',      price:15999,  orig:17999,  disc:11, rating:4.6, rev:1203, seller:'Adidas Sports',      emoji:'🏃', stock:14,  brand:'Adidas',  desc:'Responsive BOOST midsole for the ultimate run.' },
  { id:9,  name:'Atomic Habits',       cat:'Books',       price:499,    orig:799,    disc:38, rating:4.9, rev:8762, seller:'Penguin Books',      emoji:'📖', stock:200, brand:'Penguin', desc:'James Clear guide to building good habits. Bestseller.' },
  { id:10, name:'LG 55 inch 4K TV',    cat:'Electronics', price:149990, orig:179990, disc:17, rating:4.8, rev:654,  seller:'LG Electronics',    emoji:'📺', stock:3,   brand:'LG',      desc:'Self-lit OLED pixels. Perfect black, infinite contrast.' },
  { id:11, name:'Fossil Gen 6 Watch',  cat:'Fashion',     price:19495,  orig:24995,  disc:22, rating:4.3, rev:521,  seller:'Fossil India',       emoji:'⌚', stock:10,  brand:'Fossil',  desc:'Wear OS with built-in Alexa and heart rate tracking.' },
  { id:12, name:'Philips Air Fryer',   cat:'Home',        price:8495,   orig:11995,  disc:29, rating:4.5, rev:2341, seller:'Philips India',      emoji:'🍳', stock:25,  brand:'Philips', desc:'Fry, bake, grill with up to 90% less fat. 1400W.' }
];

var filteredProducts = allProducts.slice();
