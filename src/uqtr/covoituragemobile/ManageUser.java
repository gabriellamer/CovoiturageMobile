package uqtr.covoituragemobile;

import model.Session;
import model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class ManageUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_user);
		
		if(getIntent().hasExtra("userId")) {
			
			EditText username   = (EditText) findViewById(R.id.user_username);
			EditText name       = (EditText) findViewById(R.id.user_name);
			EditText lastname   = (EditText) findViewById(R.id.user_lastname);
			EditText phone      = (EditText) findViewById(R.id.user_phone);
			EditText email      = (EditText) findViewById(R.id.user_email);
			RadioButton sex     = (RadioButton) findViewById(R.id.user_man);
			EditText age        = (EditText) findViewById(R.id.user_age);
			EditText streetnbr  = (EditText) findViewById(R.id.user_street_nbr);
			EditText streetname = (EditText) findViewById(R.id.user_streetname);
			EditText city       = (EditText) findViewById(R.id.user_city);
			EditText province   = (EditText) findViewById(R.id.user_province);
			
			User currentUser = Session.getCurrentUser();
			
			username.setText(currentUser.getUsername());
			name.setText(currentUser.getName());
			lastname.setText(currentUser.getLastName());
			phone.setText(currentUser.getPhone());
			email.setText(currentUser.getEmail());
			sex.setText(currentUser.getSex());
			age.setText(currentUser.getAge());
			streetnbr.setText(currentUser.getAddress().getStreetNb());
			streetname.setText(currentUser.getAddress().getStreetName());
			city.setText(currentUser.getAddress().getCity());
			province.setText(currentUser.getAddress().getProvince());

			final Button btnDelete = (Button) findViewById(R.id.btnDelUser);
			btnDelete.setVisibility(View.VISIBLE);
			btnDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteUser(getIntent().getStringExtra("userId"));
				}
			});
		}
		
		final Button btnSave = (Button) findViewById(R.id.btnSaveUser);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userId = getIntent().getStringExtra("userId");
				
				if(userId != null && !userId.isEmpty()) {
					modifyUser();
				} else {
					createUser();
				}
			}
		});
	}
	
	private void deleteUser(String userId) {
		
		//TODO lien avec le serveur
		Session.setCurrentUser(null);
		
		Intent logoutIntent = new Intent(this, Login.class);
		startActivity(logoutIntent);
	}
	
	private void createUser() {
//		EditText username   = (EditText) findViewById(R.id.user_username);
//		EditText name       = (EditText) findViewById(R.id.user_name);
//		EditText lastname   = (EditText) findViewById(R.id.user_lastname);
//		EditText phone      = (EditText) findViewById(R.id.user_phone);
//		EditText email      = (EditText) findViewById(R.id.user_email);
//		RadioButton sex     = (RadioButton) findViewById(R.id.user_man);
//		EditText age        = (EditText) findViewById(R.id.user_age);
//		EditText streetnbr  = (EditText) findViewById(R.id.user_street_nbr);
//		EditText streetname = (EditText) findViewById(R.id.user_streetname);
//		EditText city       = (EditText) findViewById(R.id.user_city);
//		EditText province   = (EditText) findViewById(R.id.user_province);
		
		
		
		
		//TODO query the server for add
		//TODO new user from db
		
		
		//Session.setCurrentUser(newUser);
		//Intent searchIntent = new Intent(this, /*TODO search activity*/);
		//startActivity(searchIntent);

	}

	private void modifyUser() {
		
		User user = Session.getCurrentUser();
		
		EditText userName   = (EditText) findViewById(R.id.user_username);
		EditText name       = (EditText) findViewById(R.id.user_name);
		EditText lastName   = (EditText) findViewById(R.id.user_lastname);
		EditText phone      = (EditText) findViewById(R.id.user_phone);
		EditText email      = (EditText) findViewById(R.id.user_email);
		RadioButton sex     = (RadioButton) findViewById(R.id.user_man);
		EditText age        = (EditText) findViewById(R.id.user_age);
		EditText streetNbr  = (EditText) findViewById(R.id.user_street_nbr);
		EditText streetName = (EditText) findViewById(R.id.user_streetname);
		EditText city       = (EditText) findViewById(R.id.user_city);
		EditText province   = (EditText) findViewById(R.id.user_province);
		
		
		user.setUsername(userName.getText().toString());
		user.setName(name.getText().toString());
		user.setLastName(lastName.getText().toString());
		user.setPhone(phone.getText().toString());
		user.setEmail(email.getText().toString());
		user.setSex(sex.isChecked()? 'm' : 'f');
		user.setAge(Integer.parseInt(age.getText().toString()));
		user.getAddress().setStreetNb(Integer.parseInt(streetNbr.getText().toString()));
		user.getAddress().setStreetName(streetName.getText().toString());
		user.getAddress().setCity(city.getText().toString());
		user.getAddress().setProvince(province.getText().toString());
		
		//TODO modify in server
	}
}
