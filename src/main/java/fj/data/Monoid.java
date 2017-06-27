package fj.data;

import fj.F;
import fj.Function;
import fj.Unit;

/**
 * <p>
 *   A `Monoid` is a `Semigroup` with a value `mempty`, which is both a
 *   left and right unit for the associative operation <code>append</code>:
 * </p>
 * <p>
 *   <code>forall x. mempty <> x = x <> mempty = x</code>
 * </p>
 * <p>
 *   `Monoid`s are commonly used as the result of fold operations, where
 *   <code>append</code> is used to combine individual results, and <code>mempty</code> gives the result
 *   of folding an empty collection of elements.
 * </p>
 */
public interface Monoid<M> extends Semigroup<M> {

  M mempty();

  default M power(M x, Integer i) {
    Integer p = i;
    M res = x;

    while (true) {
      if (p <= 0) return mempty();

      if (p == 1) return res;

      if (p % 2 == 0) {
        p = p / 2;
        res = append(res, res);
      }
      else {
        p = p / 2;
        res = append(res, append(res, x));
      }
    }
  }

  static UnitMonoid unit() { return () -> {}; }

  static <A, B> FMonoid<A, B> f(Monoid<B> M) { return () -> M; }

  static StringMonoid string() { return () -> {}; }

  static <A> ListMonoid<A> list() { return () -> {}; }

  interface UnitMonoid extends Monoid<Unit>, UnitSemigroup {
    @Override
    default Unit mempty() { return Unit.unit(); }
  }

  interface FMonoid<S, S_> extends Monoid<F<S, S_>>, FSemigroup<S, S_> {
    Monoid<S_> M();

    @Override
    default Semigroup<S_> S() { return M(); }

    @Override
    default F<S, S_> mempty() {
      return Function.constant(M().mempty());
    }
  }

  interface StringMonoid extends Monoid<String>, StringSemigroup {
    @Override
    default String mempty() { return ""; }
  }

  interface ListMonoid<A> extends Monoid<List<A>>, ListSemigroup<A> {
    @Override
    default List<A> mempty() { return List.nil(); }
  }
}
