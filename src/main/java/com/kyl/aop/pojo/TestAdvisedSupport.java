package com.kyl.aop.pojo;

public class TestAdvisedSupport {
    public static void main(String[] args) {
        /*
        所有的Spring代理都持有AdvisedSupport，里面记录了切面的配置信息（BeanFactoryAspectJAdvisorsBuilder持有的List<Advisor>的子集，
        调用代理方法时遍历每个Advisor，根据Pointcut判断是否匹配，
        匹配上的转位MethodInterceptor，形成一个调用链List<MethodInterceptor>, 由调用链临时形成MethodInvocation（调用上下文），执行调用链的proceed方法
         */
    }
}
