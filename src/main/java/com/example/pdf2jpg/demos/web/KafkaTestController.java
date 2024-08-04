package com.example.pdf2jpg.demos.web;

import com.example.pdf2jpg.demos.dto.JobDatasourceKafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    @PostMapping("/test")

    public String dataSourceTest(@RequestBody JobDatasourceKafkaDto kafkaDto) throws IOException {
        return test(kafkaDto);
    }

    static String test(JobDatasourceKafkaDto dto) {
        String topic = dto.getTopic();
        String brokeList = dto.getBrokeList();
        String group = dto.getGrp();

        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(brokeList) || StringUtils.isEmpty(group)) {
            return "参数为空";
        }
        Properties properties = new Properties();
        // key反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        // value反系列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        // 提交方式
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 指定broker地址多个中间用,拆分，来找到group的coordinator
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokeList);
        // 指定用户组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        //topic列表
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        //循环判断是否存在
        for (String e : consumer.listTopics().keySet()) {
            System.out.println(e);
            if (topic.equals(e)) {
                return "测试连接成功";
            }
        }
        return "测试连接失败";
    }
}
