package net.thumbtack.school.concert.service;

import java.util.Set;
import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class UserService {

	/**
	 * Add new User (Register) in to DataBase
	 * 
	 * @param newUser
	 * @return
	 * @throws ServerException
	 */
	public LoginUserDtoResponse registerUser(RegisterUserDtoRequest newUser) throws ServerException {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        
        Set<ConstraintViolation<RegisterUserDtoRequest>> violations = validator.validate(newUser);
        if (violations!=null) {
        	StringBuilder err = new StringBuilder();
	        for (ConstraintViolation<RegisterUserDtoRequest> cv : violations) {
	            //System.err.println(cv.getMessage());
	            err.append(cv.getMessage());
	        }
	        throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
        }
		
        //TODO add new user in DB
        

        return loginUser(new LoginUserDtoRequest(newUser.getLogin(), newUser.getPassword()));
	}

	/**
	 * Find user in DB and check password and create new user session
	 * 
	 * @param user
	 * @return
	 * @throws ServerException
	 */
	public LoginUserDtoResponse loginUser(LoginUserDtoRequest user) throws ServerException {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        
        Set<ConstraintViolation<LoginUserDtoRequest>> violations = validator.validate(user);
        if (violations!=null) {
        	StringBuilder err = new StringBuilder();
	        for (ConstraintViolation<LoginUserDtoRequest> cv : violations) {
	            err.append(cv.getMessage());
	        }
	        throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
        }
        
        // TODO find User&password in DB
        
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		
		// TODO add uuid to session in DB
		
		return new LoginUserDtoResponse(uuidString);
	}
	
	
	
	

}
