var app = {

    initialize: function() {

        //打开摄像头拍照
        var getPicByCamera = document.getElementById("getPicByCamera");
        getPicByCamera.onclick = function() {
            var configObj = {
                action: "getPic",
                callback: "handlePicFromCamera",
                //parameter非必填，不配的话默认choice
                parameter: {
                    source: "camera"
                }
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //打开相册选取一张照片
        var getPicByLibrary = document.getElementById("getPicByLibrary");
        getPicByLibrary.onclick = function() {
            var configObj = {
                action: "getPic",
                callback: "handlePicFromLibrary",
                //parameter非必填，不配的话默认choice
                parameter: {
                    source: "library"
                }
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };


        //选择器选择
        var getPicByChoice = document.getElementById("getPicByChoice");
        getPicByChoice.onclick = function() {
            var configObj = {
                action: "getPic",
                callback: "handlePicFromChoice",
                //parameter非必填，不配的话默认choice
                parameter: {
                    source: "choice"
                }
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //扫描二维码
        var scanBarcode = document.getElementById("scanBarcode");
        scanBarcode.onclick = function() {
            var configObj = {
                action: "scanBarcode",
                callback: "handleBarcode"
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //选择一个联系人
        var selectOneContact = document.getElementById("selectOneContact");
        selectOneContact.onclick = function() {
            var configObj = {
                action: "selectOneContact",
                callback: "handleSelectedContact"
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //读取通讯录联系人数据
        var getContacts = document.getElementById("getContacts");
        getContacts.onclick = function() {
            var configObj = {
                action: "getContacts",
                callback: "handleContacts"
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //获取手机设备信息
        var getDeviceInfo = document.getElementById("getDeviceInfo");
        getDeviceInfo.onclick = function() {
            var configObj = {
                action: "getDeviceInfo",
                callback: "handleDeviceInfo"
            };
            YHXXJSInterface.exec(JSON.stringify(configObj));
        };


        //获取手机网络状态信息
        var getNetworkStatus = document.getElementById("getNetworkStatus");
        getNetworkStatus.onclick = function() {
            var configObj = {
                action: "getNetworkStatus",
                callback: "handleNetworkStatus"
            };

            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //发送短信
        var sendSMS = document.getElementById("sendSMS");
        sendSMS.onclick = function() {
            var configObj = {
                //调用发送短信接口，action必须配置为sendSMS
                action: "sendSMS",
                //phoneNum必填，smsContent非必填。
                parameter: {
                    phoneNum: "10086,10010",
                    smsContent: "江西移动JSBrige接口测试"
                }
            };

            CQXJSInterface.exec(JSON.stringify(configObj));
        };


        //打电话
        var tel = document.getElementById("tel");
        tel.onclick = function() {
            var configObj = {
                //调用拨打电话接口，action必须配置为tel
                action: "tel",
                //parameter必填
                parameter: {
                    phoneNum: "10086"
                }
            };

            CQXJSInterface.exec(JSON.stringify(configObj));
        };

        //截屏
        var capture = document.getElementById("capture");
        capture.onclick = function() {
            var configObj = {
                action: "capture",
                callback: "handleCapturePic"
            };

            YHXXJSInterface.exec(JSON.stringify(configObj));
        };


        //分享
        var sharesdk = document.getElementById("sharesdk");
        sharesdk.onclick = function() {
            var configObj = {
                action: "share",
                parameter: {
                    shareType: "URL",
                    shareTitle: "江西移动标题",
                    shareContent: "江西移动内容XXXXXXXXXXXXXXXXX",
                    shareURL: "http://www.100086.com",
                    shareIconURL: "http://jquery.cuishifeng.cn/images/head_4fa80ea.jpg"
                }
            };

            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

        //拉起百度地图
        var baidumap = document.getElementById("baidumap");
        baidumap.onclick = function() {
            var configObj = {
                action: "handleOpenApp",
                parameter: {
                  AppScheme: "baidumap://",
                  AppUrl:"map/direction?origin=34.264642646862,108.95108518068&destination=40.007623,116.360582&mode=d23/23riving&src=webapp.navi.yourCompanyName.yourAppName"
                }
            };

            YHXXJSInterface.exec(JSON.stringify(configObj));
        };


        //关闭当前页面
        var closeWeb = document.getElementById("closeWeb");
        closeWeb.onclick = function() {
            var configObj = {
                action: "closeWeb"
            };

            YHXXJSInterface.exec(JSON.stringify(configObj));
        };

    }

};



function handlePicFromCamera(backData) {
    /**
    {
        status:"success/fail",
        data:"图片的BASE64编码数据"
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var image1 = document.getElementById('myImage1');
        image1.src = "data:image/jpeg;base64," + backDataObj.data;
    }
}

function handlePicFromLibrary(backData) {
    /**
    {
        status:"success/fail",
        data:"图片的BASE64编码数据"
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var image2 = document.getElementById('myImage2');
        image2.src = "data:image/jpeg;base64," + backDataObj.data;
    }
}

function handlePicFromChoice(backData) {
    /**
    {
        status:"success/fail",
        data:"图片的BASE64编码数据"
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var image3 = document.getElementById('myImage3');
        image3.src = "data:image/jpeg;base64," + backDataObj.data;
    }
}

function handleBarcode(backData) {
    /**
    {
        status:"success/fail",
        data:"扫描到的二维码/条形码原始信息，string格式"
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        alert("扫描到的二维码/条形码信息：" + backDataObj.data);
    }
}


function handleSelectedContact(backData) {
    /**
    {
        status:"success/fail",
        data:{
            displayName:"姓名",
            phoneNum:"电话号码，如果存在多个号码，使用逗号相隔"
        }
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        alert("姓名：" + backDataObj.data.displayName + "\n电话：" + backDataObj.data.phoneNum);
    }
}


function handleContacts(backData) {
    /**
    {
        status:"success/fail",
        data:[
            {
                displayName:"姓名",
                phoneNum:"电话号码，多个号码用逗号相隔"
            },
            {
                displayName:"姓名",
                phoneNum:"电话号码，多个号码用逗号相隔"
            }
        ]
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var contactInfo = "";

        //循环联系人数据
        backDataObj.data.forEach(function(element) {
            contactInfo = contactInfo + "姓名：" + element.displayName + "\n电话：" + element.phoneNum + " \n----------\n";
        }, this);

        alert(contactInfo);
    }
}


function handleDeviceInfo(backData) {
    /**
    返回数据报文格式：
    {
        status:"success/fail",
        data:{
                brand:"手机品牌",
                model:"手机型号",
                uuid:"设备号",
                os:"操作系统类型"
                osVersion:"操作系统版本号"
            }
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var deviceInfo = "手机品牌：" + backDataObj.data.brand +
            "\n手机型号：" + backDataObj.data.model +
            "\n设备号：" + backDataObj.data.uuid +
            "\n操作系统：" + backDataObj.data.os +
            "\n操作系统版本号：" +
            backDataObj.data.osVersion;
        alert(deviceInfo);
    }
}


function handleNetworkStatus(backData) {
    /**
    返回数据报文格式：
     {
        status:"success/fail",
        data:{
                isConnected:"网络连接是否已连接 1/0",
                isWIFI:"是否使用wifi访问 1/0"
            }
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {

        var networkInfo = "网络连接状态：" + (backDataObj.data.isConnected == "1" ? "已连接" : "未连接") +
            "\n是否WIFI网络：" + (backDataObj.data.isWIFI == "1" ? "WIFI" : "非WIFI");

        alert(networkInfo);
    }
}


function handleCapturePic(backData) {
    /**
    {
        status:"success/fail",
        data:"图片的BASE64编码数据"
    }
    */
    var backDataObj = JSON.parse(backData);
    if (backDataObj.status == "success") {
        var image4 = document.getElementById('myImage4');
        image4.src = "data:image/jpeg;base64," + backDataObj.data;
    }
}


// function handleAPPInfo(backData) {
//     /**
//     {
//         status:"success/fail",
//         data:{
//                 appVersion:"app的版本号"
//             }
//     }
//     */
//     var backDataObj = JSON.parse(backData);
//     if (backDataObj.status == "success") {
//         alert("当前APP版本号：" + backDataObj.data.appVersion);
//     }
// }


app.initialize();