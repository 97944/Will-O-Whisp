package wow.app.tweet;

import java.time.LocalDateTime;
import java.util.Comparator;

import wow.domain.model.Tweet;

public class TimeLineComparator implements Comparator<Tweet> {

	@Override
	public int compare(Tweet o1, Tweet o2) {
		LocalDateTime no1 = o1.getTime();
		LocalDateTime no2 = o2.getTime();
		
		return no2.compareTo(no1);
	}

}
