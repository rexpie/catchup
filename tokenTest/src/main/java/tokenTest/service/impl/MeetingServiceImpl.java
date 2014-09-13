/**
 * 
 */
package tokenTest.service.impl;

import antlr.collections.List;
import tokenTest.response.MeetingResponse;
import tokenTest.service.MeetingServiceInterface;

/**
 * @author pengtao
 *
 */
public class MeetingServiceImpl implements MeetingServiceInterface {

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#getMeetingList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getMeetingList(String latitude, String longtitude,
			String pagenum) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#getMeetingList(java.lang.Long, java.lang.String, java.lang.String)
	 */
	public List getMeetingList(Long id, String token, String pagenum) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#startMeeting(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, int)
	 */
	public MeetingResponse startMeeting(Long id, String token, Long shopid,
			String sex_preference, String description, int time_limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#getMeetingDetail(java.lang.Long)
	 */
	public MeetingResponse getMeetingDetail(Long meetingid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#applyForMeeting(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public String applyForMeeting(Long id, String token, Long meetingid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#approveMeetingApply(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public String approveMeetingApply(Long id, String token, Long meetingid,
			Long applyid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#disapproveMeetingApply(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public String disapproveMeetingApply(Long id, String token, Long meetingid,
			Long applyid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tokenTest.service.MeetingServiceInterface#commentOnMeeting(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String)
	 *ÔÝÊ±²»×ö
	 */
	public String commentOnMeeting(Long id, String token, Long meetingid,
			String comment) {
		// TODO Auto-generated method stub
		return null;
	}

}
