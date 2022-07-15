package com.hyf.juc.test;

/**
 * 查看lock指令
 * <p>
 * 添加：C:\Program Files\Java\jdk1.8.0_181\jre\bin\server\hsdis-amd64.dll
 * <p>
 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=compileonly,*Test_Volatile3.prepareData
 * <p>
 * - 查看 Test_Volatile3 所有方法的汇编代码
 * java -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly Test_Volatile3 > localFileName
 * - 查看 add 方法的汇编代码
 * java -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*Test_Volatile3.main Test_Volatile3
 *
 * @author baB_hyf
 * @date 2020/12/15
 */
public class Test_Volatile3 {

    public static volatile boolean flag = false;

    public static void main(String[] args) {
        flag = true;
    }
}
