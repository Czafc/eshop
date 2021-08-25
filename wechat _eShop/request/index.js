//同事发送异步请求的次数
let ajaxTimes = 0;
//公共url，即服务器域名（线上为域名，本地测试时还包括端口号）
let baseURL = "http://localhost:8080";

//用于发送get请求
export const GET=(params)=>{
  ajaxTimes += 1;
  wx.showLoading({
    title: '加载中',
    mask: true
  });
  return new Promise((resolve,reject)=>{
    wx.request({
      ...params,
      url :baseURL + params.url,
      data:params.data,
      success:(result)=>{
        resolve(result);
      },
      fail:(err)=>{
        reject(err);
      },
      complete:()=>{
        ajaxTimes -= 1;
        if(ajaxTimes === 0){
          wx.hideLoading();
        } 
      }
    })
  })
}

//用于发送post请求
export const POST=(params)=>{
  wx.showLoading({
    title:'加载中',
    mask:true
  });
  return new Promise((resolve,reject) => {
    wx.request({
      ...params,
      url: baseURL + params.url,
      data: params.data,
      method:'POST',
      success:(res) => {
        resolve(res);
      },
      fail:(err) => {
        reject(err);
      },
      complete:()=>{
        wx.hideLoading();
      }
    })
  })
}