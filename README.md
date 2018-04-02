# SDKDemo

| √:必传参数<br/>×:非必参数<br/>o:可在application初始化 | 说明                                                                       | 本地生成支付订单 | 服务器生成支付订单 |
|-----------------------|----------------------------------------------------------------------------|:----------------:|:------------------:|
| setAppId              | 开发者应用id|         √-o        |          ×         |
| setNotifyUrl          | 支付宝通知商户服务器的地址                                                 |         √        |          ×         |
| setPid                | 商户号                                                                     |         √-o        |          ×         |
| setSiYao              | 私钥                                                                       |         √-o        |          ×         |
| setOut_trade_no       | 自己服务器生成的订单号                                                     |         √        |          ×         |
| setTotal_amount       | 订单金额(单位:元)精确到小数点后两位<br/>取值范围[0.01,100000000]               |         √        |          ×         |
| setSubject            | 商品的标题/交易标题/订单标题/订单关键字等                                  |         √        |          ×         |
| setBody               | 订单具体描述信息                                                           |         √        |          ×         |
| setOrderInfo          | 请求支付的订单信息                                                         |         ×        |          √         |




**上述参数中的appid,pid,siyao可在application中调用MyAliPay.setConfig(APPID,PID,SIYAO)设置 **
