/*
lower bound [T >: S]
Here T is a Type Parameter ans S is a type.
By declaring Lower Bound like “[T >: S]” means
this Type Parameter T must be either same as S or Super-Type of S.
*/

class  Animal
class Dog extends Animal
class Puppy extends Animal
class MyPuppy extends Animal

class AnimalCarer{
  def display [T >: Puppy](t: T){
    println(t)
  }
}


val animal = new Animal
val dog = new Dog
val puppy = new Puppy

val animalCarer = new AnimalCarer

animalCarer.display(animal)
animalCarer.display(puppy)
animalCarer.display(dog)

animalCarer.display(new MyPuppy)

