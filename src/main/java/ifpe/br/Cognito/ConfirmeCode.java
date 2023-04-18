package ifpe.br.Cognito;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import ifpe.br.Cognito.model.Request;
import ifpe.br.Cognito.model.Response;

public class ConfirmeCode implements RequestHandler<Request, Response>{

	@Override
	public Response handleRequest(Request input, Context context) {
		this.confirmSignUp(input.getEmail(), input.getCode());
		Response response = new Response();
		response.setMessage("Verificado com sucesso");
		
		return response;
	}

	
	private AWSCognitoIdentityProvider createCognitoClient() {
	    AWSCredentials cred = new BasicAWSCredentials("aws_access_key_id", "secretAccessKey");
	    AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
	    return AWSCognitoIdentityProviderClientBuilder.standard()
	            .withCredentials(credProvider)
	            .withRegion("us-east-1")
	            .build();
	}
	public ConfirmSignUpResult confirmSignUp(String email, String confirmationCode) {
	    ConfirmSignUpRequest confirmSignUpRequest = new                 
	    ConfirmSignUpRequest().withClientId("CLIENT_ID").withUsername(email).withConfirmationCode(confirmationCode);
	    return this.createCognitoClient().confirmSignUp(confirmSignUpRequest);
	}
	
	
}
	
