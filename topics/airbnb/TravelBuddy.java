/**
Travel Buddy
I have a wish list of cities that I want to visit to, my friends also have city wish lists that they want to visit to. If one of my friends share more than 50% (over his city count in his wish list), he is my buddy.

Given a list of city wish list, output buddy list sorting by similarity.

followup 的话写了另一个 recommend 城市的 function,好像是你的 travel buddy 想去的城市但你
不想去的按某个顺序 output 之类的,具体我忘了。
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=218938

followup是给了一个max值,找出你的buddy的wishlist里不在你的wishlist里的最多max个城市,根
据buddy和你的重合程度来排序

例如 你的wishlist是 a,b,c,d
buddy1 的wishlist 是 a,b,e,f, 有两个和你的一样,所以是你的buddy
buddy2 的wishlist 是 a,c,d,g, 有三个和你的一样,也是你的buddy
问题是输出一个size最多为max的推荐城市列表 当size为10时,buddy1和buddy2的wishlist中不在你
的wishlist中的城市都可以加入推荐中,因为buddy2的重合度更高,所以先输出buddy2中的,所以
推荐为 g,e,f
当size为2时,推荐是g,e 或 g,f
*/

/**
Solution: Use hashSet, Traverse all friends' lists, find common with myList.
How to Arrive:
* Use a hashSet to store myList, so O(1) in checking city in myList.
* Create a Buddy class, with name, similarity and wishList. Implement with Comparable.
* We want the Buddy List to be sorted in DESC order of similarity count. so (other.similarity - this.similarity;)
* Loop every friend's list, count similarities by checking same city in myList hashSet. (Can use retainAll to find intersections between myList and friend's list);
* Check the similarity count > (myList.size / 2); (> 50%); if yes add it to buddyList.
* Return sorted buddyList, using Collection.sort(), with Buddy class's compareTo() function. (compare similarity DESC);
* 
* Time: O(n); number of friends' cities.
* Space: O(n)

Follow Up:
Solution: Loop through buddyList, sort by similarity, removeAll elms intersects with myList. Add city 1 by 1, Until maxSize reached, or no more city.
* Time: O(n); number of buddy's cities.
* Space: O(n);
*/

import java.io.*;
import java.util.*;

public class TravelBuddy {

	// implement a Buddy class with comparable
	public class Buddy implements Comparable<Buddy> {
		String name;
		int similarity;
		Set<String> wishList;

		Buddy(String name, int similarity, Set<String> wishList) {
      this.name = name;
      this.similarity = similarity;
      this.wishList = wishList;
    }

    @Override
    public int compareTo(Buddy buddy) {
    	// we want higher similarity in the front, DESC
    	return buddy.similarity - this.similarity;
    }

	}

	// res list
	List<Buddy> buddyList = new ArrayList<>();
	
	/**
	 * Solution: Use hashSet, Traverse all friends' lists, find common with myList.
	 * Time: O(n); number of friends' cities.
	 * Space: O(n)
	 */
	public List<Buddy> travelBuddy(Set<String> myList, Map<String, Set<String>> friendsList) {
		if (myList == null || myList.isEmpty() || friendsList == null || friendsList.isEmpty()) {
			return new ArrayList<>();
		}
		// create new Buddy with similarities > 50%.
		for (String name : friendsList.keySet()) {
			// get cur friend's list
			Set<String> wishList = friendsList.get(name);
			// new set of intersectons between myList and friend's list.
			Set<String> commons = new HashSet<>(wishList);
			// retains elms in mylist. remove wishList' elms not in mylist.
			commons.retainAll(myList);
			int similarity = commons.size();
			// check if > 50%, half of myList's size.
			if (similarity > myList.size() / 2) {
				// add to buddyList
				buddyList.add(new Buddy(name, similarity, wishList));
			}
		}
		return sortBuddy(buddyList);
	}

	// sort travel buddy list
	public List<Buddy> sortBuddy(List<Buddy> list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Follow Up: Given a maxSize of the recommended cities, 
	 * return the maxSize list of recommended cities Not in your list, but in your Buddies' lists,
	 * order the cities by Buddy's similarity. The higher similarity Buddy's cities are in front,
	 * follow by lower similarity Buddy's cities. (until maxSize reached);
	 Solution: Loop through buddyList, sort by similarity, removeAll elms intersects with myList, add city 1 by 1. Until maxSize reached, or no more city.
	 * Time: O(n); number of buddy's cities.
	 * Space: O(n);
	 */
	public List<String> recommendedCities(List<Buddy> buddyList, Set<String> myList, int maxSize) {
		List<String> recommends = new ArrayList<>();
		List<Buddy> buddies = sortBuddy(buddyList);
		// loop buddylist sorted by similarity, get cities not in myList
		for (Buddy buddy : buddies) {
			// get buddy's list
			Set<String> cityList = buddy.wishList;
			// remove elms intersects with myList.
			cityList.removeAll(myList);
			// add cities 1 by 1, check maxSize reached
			for (String city : cityList) {
				// size reached.
				if (recommends.size() >= maxSize) {
					break;
				}
				recommends.add(city);
			}
		}
		return recommends;
	}


	public static void main(String[] args) {
		TravelBuddy obj = new TravelBuddy();
		Set<String> myWishList = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d", "e"}));
    Set<String> wishList1 = new HashSet<>(Arrays.asList(new String[]{"f", "c", "d", "a", "h", "k", "l"}));
    Set<String> wishList2 = new HashSet<>(Arrays.asList(new String[]{"a", "b", "e", "g", "d", "i", "j"}));
    Set<String> wishList3 = new HashSet<>(Arrays.asList(new String[]{"c", "f", "e", "g"}));

   	Map<String, Set<String>> friendsList = new HashMap<>();
   	friendsList.put("B1", wishList1);
   	friendsList.put("B3", wishList3);
   	friendsList.put("B2", wishList2);

   	List<TravelBuddy.Buddy> buddyList = obj.travelBuddy(myWishList, friendsList);
   	buddyList.forEach(buddy -> {
   		System.out.println(buddy.name);
   	});

   	List<String> recommends = obj.recommendedCities(buddyList, myWishList, 5);
   	recommends.forEach(city -> {
   		System.out.println(city);
   	});
	}


}