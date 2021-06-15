package backend.services;

import backend.models.LabAdmin;

public interface ILabAdminService extends IService<LabAdmin>{
	
	LabAdmin findByEmail(String email);
	
}
