/*
upper bound [T <: S]
Here T is a Type Parameter ans S is a type.
By declaring Upper Bound like “[T <: S]” means
this Type Parameter T must be either same as S or Sub-Type of S.
 */

class Animal
class Dog extends Animal
class Puppy extends Dog

class AnimalCarer{
  def display [T <: Dog](t: T){
    println(t)
  }
}


val animal = new Animal
val dog = new Dog
val puppy = new Puppy

val animalCarer = new AnimalCarer
// Dog의 상위 타입이므로 컴파일 실패
//animalCarer.display(animal)
animalCarer.display(dog)
animalCarer.display(puppy)


