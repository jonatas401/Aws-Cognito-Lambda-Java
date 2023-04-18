package ifpe.br.Cognito;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import ifpe.br.Cognito.model.Request;
import ifpe.br.Cognito.model.Response;

public class Create implements RequestHandler<Request, Response>{

	@Override
	public Response handleRequest(Request input, Context context) {
		System.out.println("input "+input.getEmail());
		System.out.println("context "+ context.getAwsRequestId());
		this.signUp(input.getName(),input.getEmail() , input.getPassword());
		
		Response response = new Response();
		response.setMessage("Criado com sucesso");
		
		return response;
	}

	private AWSCognitoIdentityProvider createCognitoClient() {
	    AWSCredentials cred = new BasicAWSCredentials("aws_access_key_id", "secretAccessKey);
	    AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
	    return AWSCognitoIdentityProviderClientBuilder.standard()
	            .withCredentials(credProvider)
	            .withRegion("us-east-1")
	            .build();
	}
	
	public SignUpResult signUp(String name, String email, String password) {
	    SignUpRequest request = new SignUpRequest().withClientId("CLIENT_ID")
	               .withUsername(email)
	               .withPassword(password)
	               .withUserAttributes(
	                    new AttributeType()
	                        .withName("name")
	                        .withValue(name));
	    SignUpResult result = createCognitoClient().signUp(request);
	    return result;
	}
	
}
