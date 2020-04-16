package org.csu.ouostore.service.config;


/**
 * 支付宝配置
 */
public class AlipayConfig {

    // 作为身份标识的应用ID
    public static final String APP_ID = "2016102300746673";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static final String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDhFE6QzoQGbkj0/f2cx+Ubkx0xr4/MJ5fRXP5qWiIfdOz4Tel1ZeBRmKVoPTf9y7sKW1isbMzcWVp+uhL8q+7VdIAHJJnePIvsaRWnAjIx8bHt8+Js4vneldOfX0SyIvQD7OP1svLWA5h3F4rlO+coJXZcCtsbz+MvOl4Y7QR7uG4n301rMiXNT4R4NSRkAUUhHzjH5HLlBVFkBWS26S4VjQkoXA54Pn1NJ2o8qyEeLdFJsVu/F/IUPCqjqM+YDhHeQq9PIyiTYHUExCXnJDET4xlRMsTGW6MYjwKsYLDd4t4PXdsH/rFaDg17yogUT0ZSzg6n+v/qb12Pc0ipb1lHAgMBAAECggEAGjKO4nPnEgiMHVfiyPjLReZdo4bXqd+B4DB+vEo5ZofpfQ00eyGcdmLZ/cQCXFv2LUJsk8UmIw+TZOSriRVJ+zZQIIS2FBPgBWhFyOaMiRukphmY5SuWAb5Wn1orWMCQvlQ4RETL1vUcLeBjnkLWqOmTWSs6TYZo3vpgMW6E8xFQabZKT4Zr16ZbnzQAAUstazAQEUjbktL8vZbGJE19YtdfWl0qoU6ZUNEVqSEWRa/HjwzPtXh0aWOsjAdEyUarZ0jUitcN0i+xkSHTEOflP562IIdN5dmvOJabhWtSQraTOCi4tdMo0/0tOaE6u9G3sCmJJHT8/ttk/Dnj83kR4QKBgQD9zyIBciEUA+cFz6fp+eJyJBZMWAqHH2tJbI62zbyosx2Cqo0Bh1Htmeth3RHj8ugO97Dqdp1X6dDOBCcmvW6YrCG00BV/fRBBQUFV0fSzbHorpGMLGZSaZJH0uOg1x0QdDhN5uS05phYG1jbjfMN3QAxeQS18YUk/fKhH/0AJ5QKBgQDjBa/fLP5uWCsJRz0ctB2GZBVMglh7jleYhrdkpUJ65wqiwh7kLsS80zzjpqefebxA0Nkms7ulg7AHY6P/jmQ/8WqMkB2c9rcsnwR79Jl4TO6TOKTSEXkjNO8W8cOqz3GVazWteNcmpjV32mLEPOrecrLg7i6FsFWk/+cNo/mzuwKBgQDVNQPe7Q2mb6TI9+iZHyW/NltY5Hhri9QnSL8WBvvdRyGaG0T6LL+A9dSKUBfwQaP4ts9yFhQUvsB933U4ENdv/uENr+LvRpK6DmQhAI8rjguOz7e3olR34dXOGupOCsdIFQP9M7YvADruoVJYhPz6Ft6M6vmXy22pnEEMwfn8lQKBgHaq1ViyOzjQq4ZJD5++YqSKEjwQ8lF7gTxARwK8A7kcpgmXsPATplERlgVlar9Iseerg6bgUljdMhlaszH1a1+lQM3C00fmCq+vW3ezzrj8dcTM2hLYgVqu4785ls7lanjxNni77rLMNKy2oePz981ZZTmAJx9IoiwCe/M8YFzbAoGAbeQyMFfF37E8k8Ae1F1E8qu94WSWJ2sChyJLVfqUFpd72PYlUBNr2WMp0H6K+2FvrHucm6lZtvzJd6DlA4wc78Hk6QO6yTvZ/wga4xzDaCIUdE9tFWczDGEHL39oRhKNE5PqU9UJ9SSxyydMQIz6cN9AIbudTqnSKg/4HClTh34=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgDd/rS0W+uJcchxiCssaNSPjqKY15VvI+5G+OiKp2W837F+2S7P5IFJZAkdrk3Yj7+PF2LdN79houY76W64NW+KTDI4tpZI/B/sc6DVcy5Ajr65oFk25coi3oBaWOGHrL4lbwGJ5bCsW6HCvCN3EwemZaap5PhnF4+EGJcsObVu2+kBUOzg3K0coLIewydu5eBIVhJCXO5C3+WuhwOt8CFB7JCrYn5NXZlm4LZc+vZndo6uaKFtZZedCqZaiumMDlnGFiJZH4a00q136OMrV2j08dtuSoyuV6eGJg7nU9OQJrvlC7PDZdFefmaXihmrEPwUKiO24TP6uR+6gHJAu1QIDAQAB";

    // 服务器异步通知(支付成功)路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String NOTIFY_URL = "http://jzx5hh.natappfree.cc/api/v1/orders/alipay/callback";

    // 页面跳转同步通知(提交支付)路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String RETURN_URL = "http://www.baidu.com";

    // 签名方式
    public static final String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static final String CHARSET = "utf-8";

    // 支付宝网关
    public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    // 编码格式
    public static final String FORMAT = "json";
}
