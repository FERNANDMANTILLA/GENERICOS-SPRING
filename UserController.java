package api.web.firebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api.web.firebase.model.User;
import api.web.firebase.model.UserDTO;
import api.web.firebase.service.UserApi;


@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserApi userApi;

    @PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody User user, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = userApi.save(user);
		} else {
			userApi.save(user, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/findall")
	public List<UserDTO> getAll() throws Exception {
		return userApi.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public UserDTO find(@PathVariable String id) throws Exception {
		List<UserDTO> users = userApi.getAll();
		for (UserDTO user : users) {
			if (user.getUsuario().equals(id)) {
				System.out.println(user.toString());
				return user;
			}
		}
		return null;
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable String id) throws Exception {
		UserDTO user = userApi.get(id);
		if (user != null) {
			userApi.delete(id);
		} else {
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
}
