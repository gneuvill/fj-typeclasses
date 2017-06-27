package fj.data;

import fj.F;
import fj.Unit;
import fj.Void;

/**
 * <p>The {@link Semigroup} type class identifies an associative operation on a type.</p>
 *
 * <p>
 *   Instances are required to satisfy the following law:
 *   <ul>
 *     <li>Associativity: <code>(x append y) append z = x append (y append z)</code></li>
 *   </ul>
 * </p>
 *
 * <p>
 *   One example of a {@link Semigroup} is {@link String}, with <code>append</code> defined as string concatenation
 * </p>
 */
public interface Semigroup<A> {

  A append(A a1, A a2);

  static StringSemigroup string() { return () -> {}; }

  static UnitSemigroup unit() { return () -> {}; }

  static VoidSemigroup voịd() { return () -> {}; }

  static <A, B> FSemigroup<A, B> f(Semigroup<B> S) { return () -> S; }

  static <A> ListSemigroup<A> list() { return () -> {}; }

  interface StringSemigroup extends Semigroup<String> {
    void self();

    @Override
    default String append(String a1, String a2) {
      return a1.concat(a2);
    }
  }

  interface UnitSemigroup extends Semigroup<Unit> {
    void self();

    @Override
    default Unit append(Unit a1, Unit a2) {
      return Unit.unit();
    }
  }

  interface VoidSemigroup extends Semigroup<Void> {
    void self();

    @Override
    default Void append(Void a1, Void a2) {
      return a1.absurd();
    }
  }

  interface FSemigroup<S, S_> extends Semigroup<F<S, S_>> {
    Semigroup<S_> S();

    @Override
    default F<S, S_> append(F<S, S_> f, F<S, S_> g) {
      return s -> S().append(f.f(s), g.f(s));
    }
  }

  interface ListSemigroup<A> extends Semigroup<List<A>> {
    void self();

    @Override
    default List<A> append(List<A> a1, List<A> a2) {
      return a1.append(a2);
    }
  }
}
