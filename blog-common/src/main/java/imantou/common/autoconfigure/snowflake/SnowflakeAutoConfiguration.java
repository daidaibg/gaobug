
package imantou.common.autoconfigure.snowflake;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * 雪花配置加载
 *
 * @author hzq
 * @date 2022/3/21
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({SnowflakeProperties.class})
public class SnowflakeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeGenerator snowflakeGenerator(SnowflakeProperties properties) {
        log.info("配置雪花生成器，workerId：[{}]，dataCenterId：[{}]", properties.getWorkerId(), properties.getDataCenterId());
        return new SnowflakeGenerator(Optional.ofNullable(properties.getWorkerId()).orElse(0L),
                Optional.ofNullable(properties.getDataCenterId()).orElse(0L));
    }
}

