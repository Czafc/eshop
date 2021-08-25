import {GET} from "../../request/index.js";
// pages/order/order.js

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orders : [],
    type : 0,
    tabs : [
      {
        id : 0,
        value:"全部订单",
        isActive : true
      },
      {
        id : 1,
        value:"待付款",
        isActive : false
      },
      {
        id : 2,
        value:"代发货",
        isActive : false
      },
      {
        id : 3,
        value:"退款/退货",
        isActive : false
      }
    ]
  },

  onUnload:function(){
    wx.switchTab({
      url: '/pages/user/user'
    })
  },

  handleTabsItemChange(e){
    //console.log(new Date().toLocaleString());
    const {index} = e.detail;
    let {tabs} = this.data;
    tabs.forEach((v,i) => i === index ? v.isActive=true:v.isActive=false);
    this.setData({
      type : index - 1,
      tabs
    })
    this.getOrderListOfUser();
  },

  onShow : function() {
    if(!wx.getStorageSync('user').userId) {
      wx.showToast({
        title: '请先登录',
        icon : 'none',
        success(res) {
          setTimeout(wx.navigateBack,1000);
        }
      })
    }else {
      let pages = getCurrentPages();
      let currentPage = pages[pages.length - 1];
      let type = Number(currentPage.options.type);
      let index = type + 1;
      let {tabs} = this.data;
      tabs.forEach((v,i) => i === index ? v.isActive = true : v.isActive=false);
      this.setData({
        tabs,
        type
      })
      this.getOrderListOfUser();
    }
  },

  getOrderListOfUser() {
    let params = {};
    params.type = this.data.type;
    params.userId = wx.getStorageSync('user').userId;
    GET({url : '/order/myOrders', data : params}).then(result => {
      this.setData({
        orders : result.data
      })
    })
  },

  handleTapOrder(e){
    if(this.data.type === 0) {
      let orderId = e.currentTarget.dataset.orderid;
      wx.navigateTo({
        url: '/pages/pay/pay?orderId='+orderId
      })
    }
  },

  //长按订单信息进行删除，待实现
  delOrderInfo(e){
    const that = this;
    wx.showModal({
      title : "提示",
      content : "删除此条订单记录",
      success(res) {
        if(res.confirm) {
          let param = {};
          param.orderId = e.currentTarget.dataset.orderid;
          GET({url : "/order/deleteOrder", data : param}).then(result =>{
            if(result.data.delResult === "success") {
              wx.showToast({
                title: '删除成功'
              })
              that.data.orders.splice(that.data.orders.findIndex(v => v.orderId === param.orderId), 1);
              let orders = that.data.orders;
              that.setData({
                orders
              })
            }else {
              wx.showToast({
                title: '删除失败',
                icon : 'none'
              })
            }
          })
        }
      }
    })
  }
})