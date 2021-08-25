import {GET} from "../../request/index.js"
// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  handleGetUserProfile(){
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (userprofile) => {
        let user = userprofile.userInfo;
        wx.login({
          success(res) {
            if(res.code) {
              GET({
                url : "/user/login",
                data : {
                  code : res.code
                }
              }).then ( result => {
                let res = result.data;
                if(res.res === "success") {
                  user.userId = res.openid;
                  wx.setStorageSync('user', user);
                  wx.showToast({
                    title: '登录成功',
                    success : function() {
                      wx.navigateBack();
                    }
                  })
                }else {
                  wx.showToast({
                    title: '登录失败',
                    icon : 'none'
                  })
                }
              })
            }
          }
        })
      }
    })
  }

})