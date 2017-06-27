package fj.data;

import fj.F;
import fj.control.Applicative;
import fj.control.Apply;
import org.derive4j.hkt.HktConfig;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

@HktConfig(typeEqMethodName = "constᐧ")
public interface Const<A, B> extends __2<Const.µ, A, B> {
  enum µ {}

  A get();

  static <A, B> Const<A, B> Const(A a) { return () -> a; }

  static <A, B> NewtypeConst<A, B> newtype() { return () -> {}; }

  static <A> FunctorConst<A> functor() { return () -> {}; }

  static <A> ApplyConst<A> apply(Semigroup<A> S) { return () -> S; }

  static <A> ApplicativeConst<A> applicative(Monoid<A> M) { return () -> M; }

  interface NewtypeConst<A, B> extends Newtype<Const<A, B>, A> {
    void self();

    @Override
    default A unwrap(Const<A, B> abConst) {
      return abConst.get();
    }

    @Override
    default Const<A, B> wrap(A a) {
      return () -> a;
    }
  }

  interface FunctorConst<X> extends Functor<__<Const.µ, X>> {
    void self();

    @Override
    default <A, B> Const<X, B> map(F<A, B> __, __<__<Const.µ, X>, A> fa) {
      return _FjData.asConst(fa)::get;
    }
  }

  interface ApplyConst<X> extends Apply<__<Const.µ, X>>, FunctorConst<X> {
    Semigroup<X> S();

    @Override
    default void self() {}

    @Override
    default <A, B> Const<X, B> apply(__<__<Const.µ, X>, F<A, B>> fab, __<__<Const.µ, X>, A> fa) {
      return () -> S().append(_FjData.asConst(fab).get(), _FjData.asConst(fa).get());
    }
  }

  interface ApplicativeConst<X> extends Applicative<__<Const.µ, X>>, ApplyConst<X> {
    Monoid<X> M();

    @Override
    default Semigroup<X> S() { return M(); }

    @Override
    default <A> Const<X, A> pure(A __) {
      return M()::mempty;
    }
  }
}