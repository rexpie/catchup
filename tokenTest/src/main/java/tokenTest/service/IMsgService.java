package tokenTest.service;

import tokenTest.response.MsgTokenResponse;

public interface IMsgService {

	public MsgTokenResponse getMsgToken(Long id, String token);
}
