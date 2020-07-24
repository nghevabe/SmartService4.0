package stackjava.com.sbjwt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.exception.AppException;
import stackjava.com.sbjwt.service.JwtService;
import stackjava.com.sbjwt.service.UserService;


@RestController
@RequestMapping("/rest")
public class UserRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	/* ---------------- GET ALL USER ------------------------ */
	@CrossOrigin
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> getAllUser() {
		return new ResponseEntity<List<UserEntity>>(userService.findAll(), HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@CrossOrigin
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		UserEntity userEntity = userService.findById(id);
		if (userEntity != null) {
			return new ResponseEntity<Object>(userEntity, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

//    /* ---------------- GET USER BY USERNAME ------------------------ */
//    @CrossOrigin
//    @RequestMapping(value = "/user_detail", method = RequestMethod.GET)
//    public ResponseEntity<Object> getUserByUsername(@RequestParam("username") String username) {
//        UserEntity userEntity = userService.loadUserByUsername(username);
//		if (userEntity != null) {
//            return new ResponseEntity<Object>(userEntity, HttpStatus.OK);
//        }else {
//			return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
//		}
//
//    }

	@CrossOrigin
	@GetMapping("/user_detail")
	public ResponseEntity<Object> getUserByUsername(@RequestParam("username") String username) {
		UserEntity userEntity = userService.getUserByUsername(username);
		if (userEntity != null) {
			return new ResponseEntity<Object>(userEntity, HttpStatus.OK);
		}
		throw new AppException("No Data");
	}

	/* ---------------- CREATE NEW USER ------------------------ */
    @CrossOrigin
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserEntity userEntity) {
		if (userService.add(userEntity)) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("User Existed!", HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable int id) {
		userService.delete(id);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

	@Autowired
	AuthenticationManager authenticationManager;

	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody UserEntity userEntity) {
		String result = "";
		HttpStatus httpStatus = null;

		try {
			if (userService.checkLogin(userEntity)) {
				result = jwtService.generateTokenLogin(userEntity.getUsername());
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

    @CrossOrigin
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(HttpServletRequest request) {


        return  "Xin Chao Test";
    }

}
