package fj.typeclasses.data.profunctor;

import fj.F;
import fj.typeclasses.control.*;
import fj.typeclasses.data.Functor;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;

public interface Star<f, A, B> extends __3<Star.µ, f, A, B> {
  enum µ {}

  __<f, B> f(A a);

  static <f> SemigroupoidStar<f> semigroupoid(Bind<f> B) { return () -> B; }

  static <f> CategoryStar<f> category(Monad<f> M) { return () -> M; }

  static <f, A> FunctorStar<f, A> functor(Functor<f> F) { return () -> F; }

  static <f, A> ApplyStar<f, A> apply(Apply<f> A) { return () -> A; }

}

interface SemigroupoidStar<f> extends Semigroupoid<__<Star.µ, f>> {
  Bind<f> B();

  @Override
  default  <B, C, D> Star<f, B, D> compose(__<__<__<Star.µ, f>, C>, D> sf, __<__<__<Star.µ, f>, B>, C> sg) {
    return b -> B().bind(_FjProF.asStar(sg).f(b), _FjProF.asStar(sf)::f);
  }
}

interface CategoryStar<f> extends SemigroupoidStar<f> , Category<__<Star.µ, f>> {
  Monad<f> M();
  @Override
  default Bind<f> B() { return M(); }

  @Override
  default  <T> Star<f, T, T> id() { return M()::<T>pure; }
}

interface FunctorStar<f, T> extends Functor<__<__<Star.µ, f>, T>> {
  Functor<f> F();

  @Override
  default <A, B> Star<f, T, B> map(F<A, B> f, __<__<__<Star.µ, f>, T>, A> g) {
    return t -> F().map(f, _FjProF.asStar(g).f(t));
  }
}

interface ApplyStar<f, T> extends FunctorStar<f, T>, Apply<__<__<Star.µ, f>, T>> {
  Apply<f> A();
  @Override
  default Functor<f> F() { return A(); }

  @Override
  default <A, B> Star<f, T, B> apply(__<__<__<Star.µ, f>, T>, F<A, B>> f, __<__<__<Star.µ, f>, T>, A> g) {
    return t -> A().apply(_FjProF.asStar(f).f(t), _FjProF.asStar(g).f(t));
  }
}

