import {GET, POST} from "../../request/index.js";
// pages/cart/cart.js
Page({
  data:{
    address:{},
    cart:[],
    allChecked:false,
    totalPrice: 0,
    totalNum:0,
    emptyCart : false,
    disabled : true
  },
  onLoad : function() {
    if(!wx.getStorageSync('user').userId) {
      wx.navigateTo({
        url: '/pages/login/login'
      })
    }
  },
  onShow:function(){
    if(wx.getStorageSync('user').userId) {
      this.setData({
        disabled : false
      })
    }
    this.getCartOfUser();
    let allChecked = false;
    let totalPrice = 0;
    let totalNum = 0;
    const address = wx.getStorageSync('address');
    this.setData({
      address,
      allChecked,
      totalNum,
      totalPrice
    })
  },

  //获取用户收货地址
  handleChooseAddress(){
    wx.chooseAddress({
      success: (result) => {
        let address = result;
        address.all = address.provinceName+address.cityName+address.countyName+address.detailInfo;
        wx.setStorageSync('address', address) 
      }
    })
  },

  //获取用户购物车数据
  getCartOfUser() {
    let userInfo = {};
    userInfo.userId = wx.getStorageSync('user').userId;
    GET({
      url:"/cart/myCart",
      data: userInfo
    }).then(result=>{
      let emptyCart = false;
      if(result.data.length != 0) {
        result.data.forEach(v=>{
          v.checked = false;
          v.changed = 0; // 购物车数据是否改变，数量改变置1，删除置2
        });
      }else {
        emptyCart = true;
      }
      this.setData({
        cart : result.data,
        emptyCart
      })
    })
  },


  // 商品选中事件
  handleCheckGoods(e){
    let index = e.currentTarget.dataset.index;
    //商品选中状态取反
    this.data.cart[index].checked = !this.data.cart[index].checked;
    let checked = this.data.cart[index].checked;
    let totalNum = this.data.totalNum;
    let totalPrice = this.data.totalPrice;
    if(checked) {
      totalNum += this.data.cart[index].productNum;
      totalPrice += this.data.cart[index].price * this.data.cart[index].productNum;
    }else {
      totalNum -= this.data.cart[index].productNum;
      totalPrice -= this.data.cart[index].price * this.data.cart[index].productNum;
      allChecked = false;
    }
    //当购物车中的商品要么被删除，要么被选中(所有没被删除的商品都被选中)时，将全选设置为true
    let allChecked = this.data.cart.every(v => v.checked || v.changed === 2);
    this.setData({
      totalNum,
      totalPrice,
      allChecked
    })
  },


  // 全选函数
  handleAllCheck(){
    //全选状态取反
    let allChecked = !this.data.allChecked;
    let totalNum = 0;
    let totalPrice = 0;
    let cart = this.data.cart;
    //选中全选
    if(allChecked) {
      cart.forEach(cartitem => {
        cartitem.checked = true;
        totalNum += cartitem.productNum;
        totalPrice += cartitem.price * cartitem.productNum;
      });
    } else {
      //取消全选
      cart.forEach(cartitem => cartitem.checked = false);
    }
    this.setData({
      cart,
      allChecked,
      totalNum,
      totalPrice
    })
  },

  // 编辑商品数量
  handleNumEdit(e){
    const {operation,index} = e.currentTarget.dataset;
    let cart = this.data.cart;
    let totalNum = this.data.totalNum;
    let totalPrice = this.data.totalPrice;
    if(cart[index].changed === 0) cart[index].changed = 1; // 将购物车数据状态设置为 “已修改”
    cart[index].productNum += operation;
    if(cart[index].checked) {
      totalNum += operation;
      totalPrice += operation * cart[index].price;
    }
    //商品数量为0时，判断是否删除商品，若不删除商品，将商品数量还原为1
    if(cart[index].productNum <= 0) {
      wx.showModal({
        title:'提示',
        content : '将此商品从购物车中移除？',
        success : (res)=> {
          let emptyCart = false;
          let allChecked = this.data.allChecked;
          if(res.confirm) {
            cart[index].changed = 2; // 将购物车数据改变状态设置为 “已删除/已结算”
            //当 “所有没有被删除的购物车项都被选中” 且 “购物车中不是所有数据都被删除” 的情况下 ，全选为真
            emptyCart = cart.every(v => v.changed === 2);
            allChecked = cart.every(v => v.changed === 2 || v.checked) && !emptyCart;
          } else {
            if(cart[index].checked) {
              totalNum += 1;
              totalPrice += cart[index].price;
            }
            cart[index].productNum = 1;
          }
          this.setData({
            allChecked,
            cart,
            totalNum,
            totalPrice,
            emptyCart
          })
          return;
        }
      })
    }
    this.setData({
      cart,
      totalNum,
      totalPrice
    });
  },

  // 生命周期函数
  onHide: function(){
    // 当退出页面时请求一次后台，避免请求次数过多
    this.updateCartOfUser();
  },

  //向后台发送POST请求，更新数据库中用户的数据
  updateCartOfUser() {
    POST({
      url : "/cart/updateCart",
      data : this.data.cart
    })
  },

  // 结算事件
  handlePay(){
    let userId = wx.getStorageSync('user').userId;
    if(!userId) {
      return;
    }
    const {address,totalNum} = this.data;
    if(totalNum === 0) {
      wx.showToast({
        title: '您还没有选购商品',
        icon: 'none'
      })
      return;
    }
    if(!address.userName) {
      wx.showToast({
        title: '请设置收货地址',
        icon: 'none'
      })
      return;
    }

    //创建订单,将订单存入本地缓存，由创建订单页面pay.wxml读取
    let order = {};
    let orderItems = [];
    this.data.cart.forEach(v => {
      if(v.checked && v.changed != 2) {
        orderItems.push(v);
      }
    })
    order.userId = wx.getStorageSync('user').userId;
    order.totalProductNum = this.data.totalNum;
    order.totalPrice = this.data.totalPrice;
    order.orderItems = orderItems;
    wx.setStorageSync('order', order);
    wx.navigateTo({
        url: '/pages/pay/pay'
    })
  }

})