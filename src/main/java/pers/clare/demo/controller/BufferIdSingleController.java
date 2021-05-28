package pers.clare.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.clare.demo.config.BufferIdConfig;
import pers.clare.bufferid.service.BufferIdService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Api(tags = {"Single Buffer ID產生測試"})
@RestController
@RequestMapping("buffer/single")
public class BufferIdSingleController extends BufferIdController {
    @Qualifier(BufferIdConfig.Single)
    @Autowired
    private BufferIdService bufferIdService;

    @ApiOperation("純數字")
    @GetMapping("number")
    public Long number(
            @ApiParam(value = "最小緩衝大小", example = "100")
            @RequestParam(defaultValue = "100") final Long minBuffer
            , @ApiParam(value = "最大緩衝大小", example = "0")
            @RequestParam(defaultValue = "0") final Long maxBuffer
            , @ApiParam(value = "ID群組", example = "id")
            @RequestParam(defaultValue = "id") final String id
            , @ApiParam(value = "ID前綴", example = "prefix")
            @RequestParam(defaultValue = "prefix") final String prefix

    ) {
        bufferIdService.save(id, prefix);
        return bufferIdService.next(minBuffer, maxBuffer, id, prefix);
    }

    @ApiOperation("前綴格式")
    @GetMapping("string")
    public String string(
            @ApiParam(value = "最小緩衝大小", example = "100")
            @RequestParam(defaultValue = "100") final Long minBuffer
            , @ApiParam(value = "最大緩衝大小", example = "0")
            @RequestParam(defaultValue = "0") final Long maxBuffer
            , @ApiParam(value = "ID群組", example = "id")
            @RequestParam(defaultValue = "id") final String id
            , @ApiParam(value = "ID前綴", example = "prefix")
            @RequestParam(defaultValue = "prefix") final String prefix
            , @ApiParam(value = "ID長度", example = "20")
            @RequestParam(defaultValue = "20") final int length

    ) {
        bufferIdService.save(id, prefix);
        return bufferIdService.next(minBuffer, maxBuffer, id, prefix, length);
    }

    @ApiOperation("純數字壓力測試")
    @GetMapping("number/test")
    public String numberTest(
            @ApiParam(value = "執行緒數量", example = "8")
            @RequestParam(defaultValue = "8") final int thread
            , @ApiParam(value = "每個執行緒產生ID數量", example = "1000000")
            @RequestParam(defaultValue = "1000000") final int count
            , @ApiParam(value = "最小緩衝大小", example = "100")
            @RequestParam(defaultValue = "100") final Long minBuffer
            , @ApiParam(value = "最大緩衝大小", example = "0")
            @RequestParam(defaultValue = "0") final Long maxBuffer
            , @ApiParam(value = "ID群組", example = "id")
            @RequestParam(defaultValue = "id") final String id
            , @ApiParam(value = "ID前綴", example = "prefix")
            @RequestParam(defaultValue = "prefix") final String prefix

    ) throws Exception {
        bufferIdService.save(id, prefix);
        return run(thread, count, () -> bufferIdService.next(minBuffer, maxBuffer, id, prefix));
    }

    @ApiOperation("前綴格式壓力測試")
    @GetMapping("string/test")
    public String stringTest(
            @ApiParam(value = "執行緒數量", example = "8")
            @RequestParam(defaultValue = "8") final int thread
            , @ApiParam(value = "每個執行緒產生ID數量", example = "1000000")
            @RequestParam(defaultValue = "1000000") final int count
            , @ApiParam(value = "最小緩衝大小", example = "100")
            @RequestParam(defaultValue = "100") final Long minBuffer
            , @ApiParam(value = "最大緩衝大小", example = "0")
            @RequestParam(defaultValue = "0") final Long maxBuffer
            , @ApiParam(value = "ID群組", example = "id")
            @RequestParam(defaultValue = "id") final String id
            , @ApiParam(value = "ID前綴", example = "prefix")
            @RequestParam(defaultValue = "prefix") final String prefix
            , @ApiParam(value = "ID長度", example = "20")
            @RequestParam(defaultValue = "20") final int length

    ) throws Exception {
        bufferIdService.save(id, prefix);
        return run2(thread, count, () -> bufferIdService.next(minBuffer, maxBuffer, id, prefix, length));
    }
}

