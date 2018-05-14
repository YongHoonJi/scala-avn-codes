class Drink

class SoftDrink extends Drink
class Cola extends SoftDrink
class TonicWater extends SoftDrink

class Juice extends Drink
class OrangeJuice extends Juice
class AppleJuice extends Juice


/* invariance */
class InvarianceClass[A] {
}
val invari1:InvarianceClass[AppleJuice] = new InvarianceClass[AppleJuice]
//val invari2:InvarianceClass[Juice] = new InvarianceClass[AppleJuice]

class VendingMachine[+A] {
  // .. don't worry about implementation yet
}

def install(softDrinkVM: VendingMachine[SoftDrink]): Unit = {
  // Installs the soft drink vending machine
}

// covariant subtyping
install(new VendingMachine[Cola])
install(new VendingMachine[TonicWater])
// invariant
install(new VendingMachine[SoftDrink])

// Compile error ! contravariant subtyping
//install(new VendingMachine[Drink])


//Scala fixes this problem by making its Array type invariant in its type parameter.
/*
final class Array[T](_length: Int) {
  // arr.update(i, x) is equivalent to arr(i) = x
  def update(i: Int, x: T): Unit = ...
  // ...
}
*/



class NewVendingMachine[+A](val currentItem: Option[A], items: List[A]) {

  def this(items: List[A]) = this(None, items)

  def dispenseNext(): NewVendingMachine[A] =
    items match {
      case Nil => {
        if (currentItem.isDefined) // isDefined : some이 아니면 false
          new NewVendingMachine(None, Nil)
        else
          this
      }
      case t :: ts => {
        new NewVendingMachine(Some(t), ts)
      }
    }
  // lower type bound : ? super T in java. It means that B is constrained to be a supertype of A.
  // VendingMachine[B] has very useful characteristic, that is, a lower bound makes the method addAll very flexible as seen below.
  def addAll[B >: A](newItems: List[B]): NewVendingMachine[B] =
    new NewVendingMachine(items ++ newItems)
}

val colasVM: NewVendingMachine[Cola] = new NewVendingMachine(List(new Cola, new Cola))
colasVM.addAll(List(new Cola))
colasVM.dispenseNext().currentItem
val softDrinksVM: NewVendingMachine[SoftDrink] = colasVM.addAll(List(new TonicWater))
val DrinkVM: NewVendingMachine[Drink] = new NewVendingMachine(Option.apply(new SoftDrink), Nil)
DrinkVM.dispenseNext().currentItem

/*
Type parameter with variance annotation (covariant + or contravariant -) can be used as mutable field type only if the field has object private scope
(private[this]). This is explained in Programming In Scala [Odersky2008].
Covariant (and contravariant) type parameter as mutable field type
 */
trait Bullet
class NormalBullet extends Bullet
class ExplosiveBullet extends Bullet

final class AmmoMagazine[+A <: Bullet](private[this] var bullets: List[A]) {

  def hasBullets: Boolean = !bullets.isEmpty

  def giveNextBullet(): Option[A] =
    bullets match {
      case Nil => {
        None
      }
      case t :: ts => {
        bullets = ts
        Some(t)
      }
    }

}

final class Gun(private var ammoMag: AmmoMagazine[Bullet]) {

  def reload(ammoMag: AmmoMagazine[Bullet]): Unit =
    this.ammoMag = ammoMag

  def hasAmmo: Boolean = ammoMag.hasBullets

  /** Returns Bullet that was shoot or None if there is ammo left */
  def shoot(): Option[Bullet] = ammoMag.giveNextBullet()

}

val gun = new Gun(new AmmoMagazine(List(new NormalBullet, new NormalBullet)))
// compiles because of covariant subtyping
gun.reload(new AmmoMagazine(List(new ExplosiveBullet)))

/** contravariance **/
class Item
class PlasticItem extends Item
class PlasticBottle extends PlasticItem
class PaperItem extends Item
class NewPaper extends PaperItem

class GarbageCan[-A] {
  // .. don't worry about implementation yet
}

def setGarbageCanForPlastic(gc: GarbageCan[PlasticItem]): Unit = {
  // sets garbage can for PlasticItem items
}

// contravariant subtyping
setGarbageCanForPlastic(new GarbageCan[Item])

// invariant
setGarbageCanForPlastic(new GarbageCan[PlasticItem])

// Compile error ! covariant subtyping
//setGarbageCanForPlastic(new GarbageCan[PlasticBottle])


/*
// a bit simplified source code from Scala API
trait Function1[-T, +R] extends AnyRef {
  def apply(v1: T): R
}
 */