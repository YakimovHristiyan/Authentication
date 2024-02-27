package sample.authentication.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sample.authentication.constants.TopicConstants;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TopicNamingService {

    public static String getTopicName(Class<?> clazz) {
        return clazz.getSimpleName() + TopicConstants.TOPIC_SUFFIX;
    }
}