package org.csu.ouostore.admin.controller;

import com.sun.management.OperatingSystemMXBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.vo.SystemInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;

/**
 *
 * @author zack
 */
@RestController
@Api(tags = "系统信息管理")
@RequestMapping("/api/v1/system")
public class SmsSystemController {

    @ApiOperation("获取cpu、内存、磁盘")
    @GetMapping("")
    public CommonResult<SystemInfoVo> getList() {
        SystemInfoVo vo = new SystemInfoVo();
//        Executors.newSingleThreadScheduledExecutor(). scheduleAtFixedRate(() -> {
            try {
                oshi.SystemInfo systemInfo = new oshi.SystemInfo();
                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                // 椎内存使用情况
                MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
                // 初始的总内存
                long initTotalMemorySize = memoryUsage.getInit();
                // 最大可用内存
                long maxMemorySize = memoryUsage.getMax();
                // 已使用的内存
                long usedMemorySize = memoryUsage.getUsed();
                // 操作系统
                String osName = System.getProperty("os.name");
                // 总的物理内存
                vo.setSystemMaxMemory(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024);
                vo.setSystemLeftMemory(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024);
                // 磁盘使用情况
                File[] files = File.listRoots();
                for (File file : files) {
                    vo.setSystemMaxStore(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024);
                    vo.setSystemLeftStore(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024);
                }
                vo.setOs(osName);
                vo.setStartTime(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
                vo.setCpuCoreCount(Runtime.getRuntime().availableProcessors());
                vo.setJvmMaxMemory(maxMemorySize * 1.0 / 1024 / 1024);
                vo.setJvmLeftMemory(maxMemorySize * 1.0 / 1024 / 1024 - usedMemorySize * 1.0 / 1024 / 1024);
                getCpuInfo(systemInfo, vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return CommonResult.OK(vo);
    }

    private void getCpuInfo(SystemInfo systemInfo, SystemInfoVo vo) throws InterruptedException {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
//        long[] prevTicks = processor.getSystemCpuLoadTicks();
//        long[] ticks = processor.getSystemCpuLoadTicks();
//        TimeUnit.SECONDS.sleep(1);
//        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()]
//                - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
//        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()]
//                - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
//        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
//                - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
//        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()]
//                - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
//        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()]
//                - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
//        long user = ticks[CentralProcessor.TickType.USER.getIndex()]
//                - prevTicks[CentralProcessor.TickType.USER.getIndex()];
//        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()]
//                - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
//        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()]
//                - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
//        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        vo.setCpuCoreCount(processor.getLogicalProcessorCount());
//        vo.setCpuSystemUsedRate(cSys * 1.0 / totalCpu);
//        vo.setCpuUserUsedRate(user * 1.0 / totalCpu);
//        vo.setCpuUnwantedRate(idle * 1.0 / totalCpu);
    }

}
