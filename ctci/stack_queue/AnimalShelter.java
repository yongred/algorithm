/**
Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly"first in, first
out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter,
or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of
that type). They cannot select which specific animal they would like. Create the data structures to
maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog,
and dequeueCat. You may use the built-in Linked List data structure.
Hints: #22, #56, #63
*/
import java.util.*;

abstract class Animal{
	String name;
	int order;
	public Animal(String name){
		this.name = name;
	}

	public int getOrder(){
		return order;
	}

	public void setOrder(int order){
		this.order = order;
	}

	public boolean isOlder(Animal ani){
		return (this.order < ani.order);
	}
}

public class AnimalShelter{
	static class Cat extends Animal{
		public Cat(String name){
			super(name);
		}
	}

 	static class Dog extends Animal{
		public Dog(String name){
			super(name);
		}
	}
	LinkedList<Cat> cats = new LinkedList<Cat>();
	LinkedList<Dog> dogs = new LinkedList<Dog>();
	int curOrder = 0;
	public void enqueue(Animal item){
		curOrder++;
		item.setOrder(curOrder);
		if(item instanceof Cat){
			cats.add((Cat)item);
		}else{
			dogs.add((Dog)item);
		}
	}
	public String dequeueCats(){
		if(cats.size() <= 0){
			throw new NoSuchElementException();
		}
		return cats.removeFirst().name;
	}
	public String dequeueDogs(){
		if(dogs.size() <= 0){
			throw new NoSuchElementException();
		}
		return dogs.removeFirst().name;
	}
	public String dequeueAny(){
		
		if(isEmpty())
			throw new NoSuchElementException();
		else if(cats.size() <= 0){
			Dog firstDog = dogs.getFirst();
			dogs.removeFirst();
			return firstDog.name;
		}
		else if(dogs.size() <= 0){
			Cat firstCat = cats.getFirst();
			cats.removeFirst();
			return firstCat.name;
		}
		else{
			Dog firstDog = dogs.getFirst();
			Cat firstCat = cats.getFirst();
			if(firstCat.isOlder(firstDog)){
				cats.removeFirst();
				return firstCat.name;
			} else{
				dogs.removeFirst();
				return firstDog.name;
			}
		}
	}

	public boolean isEmpty(){
		return cats.size() <= 0 && dogs.size() <=0;
	}

	public static void main(String [] args){
		AnimalShelter obj = new AnimalShelter();
		obj.enqueue(new Cat("A cat"));
		obj.enqueue(new Cat("B cat"));
		obj.enqueue(new Dog("A Dog"));
		obj.enqueue(new Dog("B Dog"));
		obj.enqueue(new Cat("C cat"));
		obj.enqueue(new Dog("C dog"));
		while(!obj.isEmpty()){
			System.out.print(" ,"+ obj.dequeueAny());
		}
	}
}//end AnimalShelter