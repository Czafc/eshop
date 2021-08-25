// pages/pay/pay.js
import {GET, POST} from "../../request/index.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address:{},
    cart : [],
    totalNum: 0,
    totalPrice: 0,
    modalHidden : true
  },
  order : {},
  //获取用户收货地址
  handleChooseAddress(){
    wx.chooseAddress({
      success: (result) => {
        let address = result;
        address.all = address.provinceName+address.cityName+address.countyName+address.detailInfo;
        wx.setStorageSync('address', address);
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let pages = getCurrentPages();
    let currentPage = pages[pages.length - 1];
    let orderId = currentPage.options.orderId;
    if(orderId) {
      console.log(orderId);
      let param = {};
      param.orderId = orderId;
      GET({url : '/order/orderDetail', data : param}).then(result =>{
        let order = result.data;
        const address = wx.getStorageSync('address');
        let cart = order.orderItems;
        let totalNum = order.totalProductNum;
        let totalPrice = order.totalPrice;
        this.order = order;
        this.setData({
          address,
          cart,
          totalPrice,
          totalNum
        })
      })
    } else {
      const address = wx.getStorageSync('address');
      let order = wx.getStorageSync('order');
      let cart = order.orderItems;
      let totalNum = order.totalProductNum;
      let totalPrice = order.totalPrice;
      this.order = order;
      this.setData({
        address,
        cart,
        totalPrice,
        totalNum
      })
    }
  },

  handlePay(){
    if(this.order.orderId) {
      this.setData ({
        modalHidden : false
      })
    }else {
      let order = this.order;
      order.address = this.data.address;
      order.createTime = new Date().toLocaleString();
      POST({url : '/order/createOrder', data : order}).then(result => {
        if(result.data.orderId) {
          this.order.orderId = result.data.orderId;
          this.setData ({
            modalHidden : false
          })
        } else {
          wx.showToast({
            title: '创建订单失败',
            icon : 'none'
          })
        }
      })
    }
  },

  payCancel(){
    this.setData({
      modalHidden : true
    });
    wx.showToast({
      title: '支付已取消'
    })
  },

  payConfirm(){
    let orderData = {
      "orderId" : this.order.orderId
    }
    GET({url : '/order/confirmPay', data : orderData}).then(result => {
      this.setData({
        modalHidden : true
      });
      if(result.data === 'success') {
        wx.showToast({
          title: '支付成功'
        })
        wx.navigateTo({
          url: '/pages/order/order?type=-1'
        })
      }else {
        wx.showToast({
          title: '支付失败',
          icon : 'none'
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  }
})