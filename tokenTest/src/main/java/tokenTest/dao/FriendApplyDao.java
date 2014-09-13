package tokenTest.dao;

import java.util.List;

import tokenTest.model.FriendApply;
import tokenTest.model.User;

public interface FriendApplyDao {
	void save(FriendApply friendApply);

	void update(FriendApply friendApply);

	void delete(FriendApply friendApply);
	
	List<FriendApply> findApplyByUser(User toUser);
}
