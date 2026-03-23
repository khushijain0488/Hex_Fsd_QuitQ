
// admin.js  —  Admin dashboard tabs and content rendering


function adminTab(el, tab) {
  var btns = document.querySelectorAll('.sidebar-btn');
  for (var i = 0; i < btns.length; i++) btns[i].classList.remove('active');
  el.classList.add('active');
  renderAdmin(tab);
}
// dummy data

function renderAdmin(tab) {
  var main = document.getElementById('admin-main');

  if (tab === 'overview') {
    main.innerHTML =
      '<div class="section-title">Admin Overview</div>' +
      '<div class="stats-grid">' +
        '<div class="stat-card"><div class="stat-val">12,480</div><div class="stat-lbl">Total Users</div></div>' +
        '<div class="stat-card"><div class="stat-val">342</div><div class="stat-lbl">Active Sellers</div></div>' +
        '<div class="stat-card"><div class="stat-val">8,921</div><div class="stat-lbl">Products</div></div>' +
        '<div class="stat-card"><div class="stat-val">Rs 48L</div><div class="stat-lbl">GMV This Month</div></div>' +
      '</div>' +
      '<div class="dash-card">' +
        '<div class="dash-card-title">Recent Activity</div>' +
        '<table class="data-table">' +
          '<thead><tr><th>Event</th><th>User</th><th>Time</th><th>Status</th></tr></thead>' +
          '<tbody>' +
            '<tr><td>New Registration</td><td>priya@gmail.com</td><td>2 min ago</td><td><span class="badge shipped">Success</span></td></tr>' +
            '<tr><td>Seller Onboarding</td><td>TechShop India</td><td>15 min ago</td><td><span class="badge pending">Pending</span></td></tr>' +
            '<tr><td>Order Placed</td><td>#ORD-9823</td><td>1 hr ago</td><td><span class="badge shipped">Success</span></td></tr>' +
            '<tr><td>Refund Request</td><td>rahul@email.com</td><td>3 hr ago</td><td><span class="badge processing">Processing</span></td></tr>' +
          '</tbody>' +
        '</table>' +
      '</div>';

  } else if (tab === 'users') {
    main.innerHTML =
      '<div class="section-title">User Management</div>' +
      '<div class="dash-card">' +
        '<div class="dash-card-title">All Users</div>' +
        '<table class="data-table">' +
          '<thead><tr><th>Name</th><th>Email</th><th>Joined</th><th>Orders</th><th>Action</th></tr></thead>' +
          '<tbody>' +
            '<tr><td>Priya Sharma</td><td>priya@gmail.com</td><td>Mar 2026</td><td>12</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'User deleted.\')">Delete</button></td></tr>' +
            '<tr><td>Rahul Verma</td><td>rahul@email.com</td><td>Feb 2026</td><td>7</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'User deleted.\')">Delete</button></td></tr>' +
            '<tr><td>Ananya Singh</td><td>ananya@yahoo.com</td><td>Jan 2026</td><td>23</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'User deleted.\')">Delete</button></td></tr>' +
            '<tr><td>Karan Mehta</td><td>karan@hotmail.com</td><td>Dec 2025</td><td>4</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'User deleted.\')">Delete</button></td></tr>' +
          '</tbody>' +
        '</table>' +
      '</div>';

  } else if (tab === 'sellers') {
    main.innerHTML =
      '<div class="section-title">Seller Management</div>' +
      '<div class="dash-card">' +
        '<div class="dash-card-title">Active Sellers</div>' +
        '<table class="data-table">' +
          '<thead><tr><th>Store</th><th>Category</th><th>Products</th><th>Revenue</th><th>Action</th></tr></thead>' +
          '<tbody>' +
            '<tr><td>Apple Store India</td><td>Electronics</td><td>48</td><td>Rs 24,80,000</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Seller suspended.\')">Suspend</button></td></tr>' +
            '<tr><td>Sony India</td><td>Electronics</td><td>112</td><td>Rs 18,90,000</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Seller suspended.\')">Suspend</button></td></tr>' +
            '<tr><td>Nike Official</td><td>Fashion</td><td>234</td><td>Rs 12,40,000</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Seller suspended.\')">Suspend</button></td></tr>' +
            '<tr><td>IKEA India</td><td>Home</td><td>540</td><td>Rs 9,80,000</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Seller suspended.\')">Suspend</button></td></tr>' +
          '</tbody>' +
        '</table>' +
      '</div>';

  } else if (tab === 'categories') {
    main.innerHTML =
      '<div class="section-title">Category Management</div>' +
      '<div class="dash-card">' +
        '<div class="dash-card-title">Categories ' +
          '<button class="small-btn" onclick="showToast(\'Category added!\')">+ Add</button>' +
        '</div>' +
        '<table class="data-table">' +
          '<thead><tr><th>Category</th><th>Products</th><th>Action</th></tr></thead>' +
          '<tbody>' +
            '<tr><td>Mobile</td><td>1,240</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Category removed.\')">Remove</button></td></tr>' +
            '<tr><td>Electronics</td><td>3,421</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Category removed.\')">Remove</button></td></tr>' +
            '<tr><td>Fashion</td><td>8,901</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Category removed.\')">Remove</button></td></tr>' +
            '<tr><td>Home</td><td>2,100</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Category removed.\')">Remove</button></td></tr>' +
            '<tr><td>Books</td><td>12,000</td><td><button style="background:none;border:none;color:red;cursor:pointer;" onclick="showToast(\'Category removed.\')">Remove</button></td></tr>' +
          '</tbody>' +
        '</table>' +
      '</div>';

  } else if (tab === 'reports') {
    main.innerHTML =
      '<div class="section-title">Platform Reports</div>' +
      '<div class="stats-grid" style="grid-template-columns:repeat(3,1fr);">' +
        '<div class="stat-card"><div class="stat-val">Rs 4.8Cr</div><div class="stat-lbl">Total GMV 2026</div></div>' +
        '<div class="stat-card"><div class="stat-val">38,421</div><div class="stat-lbl">Total Orders</div></div>' +
        '<div class="stat-card"><div class="stat-val">+23%</div><div class="stat-lbl">MoM Growth</div></div>' +
      '</div>' +
      '<div class="dash-card" style="margin-top:18px;">' +
        '<div class="dash-card-title">Monthly Sales Report</div>' +
        '<table class="data-table">' +
          '<thead><tr><th>Month</th><th>Orders</th><th>Revenue</th><th>Avg Order</th></tr></thead>' +
          '<tbody>' +
            '<tr><td>March 2026</td><td>4,821</td><td>Rs 48,21,000</td><td>Rs 9,997</td></tr>' +
            '<tr><td>February 2026</td><td>3,912</td><td>Rs 39,12,000</td><td>Rs 10,001</td></tr>' +
            '<tr><td>January 2026</td><td>4,123</td><td>Rs 41,23,000</td><td>Rs 9,999</td></tr>' +
          '</tbody>' +
        '</table>' +
      '</div>';
  }
}
