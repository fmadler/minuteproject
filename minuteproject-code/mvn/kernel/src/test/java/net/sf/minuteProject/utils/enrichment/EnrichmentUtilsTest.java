package net.sf.minuteProject.utils.enrichment;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.mock.TemplateMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

public class EnrichmentUtilsTest {

    static class IsToGenerateBasedOnPropertyPresenceAndValueArgumentProvider implements ArgumentsProvider {

        Template templateNoPresence = TemplateMock.getTemplateWithPropPresence("securityAuthenticationType", "cas");
        Template templateMatchPresence = TemplateMock.getTemplateWithPropPresenceWithProperty("securityAuthenticationType", "cas", TemplateMock.getPropSecurityAuthenticationTypeCas());
        Template templateUnmatchPresence = TemplateMock.getTemplateWithPropPresenceWithProperty("securityAuthenticationType", "cas", TemplateMock.getPropSecurityAuthenticationTypeBasic());

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(templateNoPresence, new Application(), Boolean.FALSE)
                    ,Arguments.of(templateMatchPresence, new Application(), Boolean.TRUE)
                    ,Arguments.of(templateUnmatchPresence, new Application(), Boolean.FALSE)
            );
        }
    }

    @Nested
    class IsToGenerateBased {

        @ParameterizedTest
        @ArgumentsSource(IsToGenerateBasedOnPropertyPresenceAndValueArgumentProvider.class)
        public void isToGenerateBasedOnPropertyPresenceAndValue(Template template, GeneratorBean bean, boolean expectedResult) {
            boolean result = EnrichmentUtils.isToGenerateBasedOnPropertyPresenceValue(template, bean);
            Assertions.assertThat(result).isEqualTo(expectedResult);
        }
    }
}
