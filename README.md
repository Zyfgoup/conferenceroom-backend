# conferenceroomback
基于Springboot+Vue+ElementUI的企业会议室管理系统的后端

2020.12.05更新
修改了一下token过期后抛出异常无法被全局异常处理的问题
请求进来会按照 filter -> interceptor -> controllerAdvice -> aspect -> controller的顺序调用
当controller返回异常 也会按照controller -> aspect -> controllerAdvice -> interceptor -> filter来依次抛出
所以Filter的抛出的异常好像走不到定义的异常处理类了 由Spring的BasicErrorController处理了
所以自己写一个ErrorController的实现类 然后在Controller层把异常抛出了 就能被捕获进行处理了


# 前端见我的另外一个conferenceroomfront仓库

