// pages/user/user.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo : {},
    hasUserInfo : false
  },

  onShow : function() {
    let hasUserInfo = this.data.hasUserInfo;
    if(!hasUserInfo) {
      let userInfo = wx.getStorageSync('user');
      if(userInfo) {
        this.setData({
          userInfo,
          hasUserInfo : true
        })
      }
    }
  }


})