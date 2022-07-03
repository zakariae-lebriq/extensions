package org.example.authenticators;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Random;

public class FormAuthenticator implements Authenticator {

    private String webApiUrl;

    public FormAuthenticator(String webApiUrl) {
        this.webApiUrl = webApiUrl;
    }

    @Override
    public void authenticate(AuthenticationFlowContext authenticationFlowContext) {

        // return the form
        int number1 = new Random().nextInt(100 - 1 + 1) + 1;
        int number2 = new Random().nextInt(100 - 1 + 1) + 1;

        authenticationFlowContext.form().setAttribute("number1", String.valueOf(number1));
        authenticationFlowContext.form().setAttribute("number2", String.valueOf(number2));
        authenticationFlowContext.form().setAttribute("web",webApiUrl);
        Response challenge = authenticationFlowContext.form().createForm("code.ftl");

        authenticationFlowContext.getAuthenticationSession().setAuthNote("number1", String.valueOf(number1));
        authenticationFlowContext.getAuthenticationSession().setAuthNote("number2", String.valueOf(number2));
        authenticationFlowContext.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
        // get data and process the authentication

        int number1 = Integer.parseInt(authenticationFlowContext.getAuthenticationSession().getAuthNote("number1"));
        int number2 = Integer.parseInt(authenticationFlowContext.getAuthenticationSession().getAuthNote("number2"));

        MultivaluedMap<String, String> form = (MultivaluedMap<String, String>) authenticationFlowContext.getHttpRequest().getDecodedFormParameters();

        int sum = Integer.valueOf(String.valueOf(form.get("sum").get(0)));

        if (sum == number1 + number2) {
            authenticationFlowContext.success();
        } else {

            int n1 = new Random().nextInt(100 - 1 + 1) + 1;
            int n2 = new Random().nextInt(100 - 1 + 1) + 1;

            authenticationFlowContext.form().setAttribute("number1", String.valueOf(n1));
            authenticationFlowContext.form().setAttribute("number2", String.valueOf(n2));
            authenticationFlowContext.form().setAttribute("web",webApiUrl);
            Response challenge = authenticationFlowContext.form().createForm("code.ftl");


            authenticationFlowContext.getAuthenticationSession().setAuthNote("number1", String.valueOf(n1));
            authenticationFlowContext.getAuthenticationSession().setAuthNote("number2", String.valueOf(n2));
            authenticationFlowContext.challenge(challenge);
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(org.keycloak.models.KeycloakSession keycloakSession, org.keycloak.models.RealmModel realmModel, org.keycloak.models.UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(org.keycloak.models.KeycloakSession keycloakSession, org.keycloak.models.RealmModel realmModel, org.keycloak.models.UserModel userModel) {

    }

    @Override
    public void close() {
        // nothing to do
    }


}
