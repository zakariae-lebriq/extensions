package org.example.authenticators;

import org.jboss.logging.Logger;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Arrays;
import java.util.List;

import static org.keycloak.provider.ProviderConfigProperty.STRING_TYPE;

public class FormAuthenticatorFactory implements AuthenticatorFactory {


    private static final String WEB = "webApiUrl";
    private String webApiUrl;

    private static final Logger LOGGER = Logger.getLogger(FormAuthenticatorFactory.class);

    @Override
    public Authenticator create(KeycloakSession session) {
        return new FormAuthenticator(this.webApiUrl);
    }

    @Override
    public void init(org.keycloak.Config.Scope scope) {
        LOGGER.warn("**************** loading config from spi scope *******************");
        webApiUrl = scope.get(WEB);
        LOGGER.warn("**************** Value loaded " + webApiUrl + " *******************");
    }

    @Override
    public String getId() {
        return "custom.form.factory";
    }

    @Override
    public String getDisplayType() {
        return "Custom form factory";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public org.keycloak.models.AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // nothing to do
    }

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        ProviderConfigProperty attr1 = new ProviderConfigProperty();
        attr1.setType(STRING_TYPE);
        attr1.setName("attr1");
        attr1.setLabel("label of attr1");
        attr1.setHelpText("help text of attr1");

        ProviderConfigProperty attr2 = new ProviderConfigProperty();
        attr2.setType(STRING_TYPE);
        attr2.setName("attr2");
        attr2.setLabel("label of attr2");
        attr2.setHelpText("help text of attr2");

        return Arrays.asList(attr1, attr2);
    }
}
