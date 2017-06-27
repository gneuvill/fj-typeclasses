package fj.data.profunctor;

import fj.F;
import fj.P;
import fj.P2;
import fj.data.Either;
import fj.control.*;
import fj.data.Functor;
import fj.data.Newtype;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;

/**
 * <p>Turns a {@link Functor} into a {@link Profunctor}.</p>
 *
 * <code>__&lt;Star.µ, f&gt;</code> is also the Kleisli category for <code>f</code>.
 */
public interface Star<f, A, B> extends __3<Star.µ, f, A, B> {
  enum µ {}

  __<f, B> f(A a);

  static <f, A, B> NewtypeStar<f, A, B> newtype() { return () -> {}; }

  static <f> SemigroupoidStar<f> semigroupoid(Bind<f> B) { return () -> B; }

  static <f> CategoryStar<f> category(Monad<f> M) { return () -> M; }

  static <f, A> FunctorStar<f, A> functor(Functor<f> F) { return () -> F; }

  static <f, A> ApplyStar<f, A> apply(Apply<f> A) { return () -> A; }

  static <f, A> ApplicativeStar<f, A> applicative(Applicative<f> A) { return () -> A; }

  static <f, A> BindStar<f, A> bind(Bind<f> B) { return () -> B; }

  static <f, A> MonadStar<f, A> monad(Monad<f> M) { return () -> M; }

  static <f> ProfunctorStar<f> profunctor(Functor<f> F) { return () -> F; }

  static <f> StrongStar<f> strong(Functor<f> F) { return () -> F; }

  static <f> ChoiceStar<f> choice(Applicative<f> A) { return () -> A; }

  interface NewtypeStar<f, A, B> extends Newtype<Star<f, A, B>, F<A, __<f, B>>> {
    void self();

    @Override
    default F<A, __<f, B>> unwrap(Star<f, A, B> star) {
      return star::f;
    }

    @Override
    default Star<f, A, B> wrap(F<A, __<f, B>> f) {
      return f::f;
    }
  }

  interface SemigroupoidStar<f> extends Semigroupoid<__<Star.µ, f>> {
    Bind<f> B();

    @Override
    default <B, C, D> Star<f, B, D> compose(__<__<__<Star.µ, f>, C>, D> sf, __<__<__<Star.µ, f>, B>, C> sg) {
      return b -> B().bind(_FjProF.asStar(sg).f(b), _FjProF.asStar(sf)::f);
    }
  }

  interface CategoryStar<f> extends SemigroupoidStar<f>, Category<__<Star.µ, f>> {
    Monad<f> M();

    @Override
    default Bind<f> B() { return M(); }

    @Override
    default <T> Star<f, T, T> id() { return M()::<T>pure; }
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

  interface ApplicativeStar<f, T> extends ApplyStar<f, T>, Applicative<__<__<Star.µ, f>, T>> {
    Applicative<f> A();

    @Override
    default <A> Star<f, T, A> pure(A a) {
      return __ -> A().pure(a);
    }
  }

  interface BindStar<f, T> extends ApplyStar<f, T>, Bind<__<__<Star.µ, f>, T>> {
    Bind<f> B();

    @Override
    default Apply<f> A() { return B(); }

    @Override
    default <A, B> Star<f, T, B> bind(__<__<__<Star.µ, f>, T>, A> ma, F<A, __<__<__<Star.µ, f>, T>, B>> f) {
      return t -> B().bind(_FjProF.asStar(ma).f(t), a -> _FjProF.asStar(f.f(a)).f(t));
    }
  }

  interface MonadStar<f, T> extends BindStar<f, T>, ApplicativeStar<f, T>, Monad<__<__<Star.µ, f>, T>> {
    Monad<f> M();

    @Override
    default Bind<f> B() { return M(); }

    @Override
    default Applicative<f> A() { return M(); }
  }

  interface ProfunctorStar<f> extends Profunctor<__<Star.µ, f>> {
    Functor<f> F();

    @Override
    default <A, B, C, D> Star<f, A, D> dimap(F<A, B> a2b, F<C, D> c2d, __<__<__<Star.µ, f>, B>, C> b2c) {
      return a -> F().map(c2d, _FjProF.asStar(b2c).f(a2b.f(a)));
    }
  }

  interface StrongStar<f> extends ProfunctorStar<f>, Strong<__<Star.µ, f>> {
    Functor<f> F();

    @Override
    default <A, B, C> Star<f, P2<A, C>, P2<B, C>> first(__<__<__<Star.µ, f>, A>, B> pab) {
      return t -> F().map(b -> P.p(b, t._2()), _FjProF.asStar(pab).f(t._1()));
    }

    @Override
    default <A, B, C> Star<f, P2<A, B>, P2<A, C>> second(__<__<__<Star.µ, f>, B>, C> pbc) {
      return t -> F().map(c -> P.p(t._1(), c), _FjProF.asStar(pbc).f(t._2()));
    }
  }

  interface ChoiceStar<f> extends ProfunctorStar<f>, Choice<__<Star.µ, f>> {
    Applicative<f> A();

    @Override
    default Functor<f> F() { return A(); }

    @Override
    default <A, B, C> Star<f, Either<A, C>, Either<B, C>> left(__<__<__<Star.µ, f>, A>, B> pab) {
      return ei -> ei
        .either(a -> A().map(Either::left, _FjProF.asStar(pab).f(a)), c -> A().pure(Either.right(c)));
    }

    @Override
    default <A, B, C> Star<f, Either<A, B>, Either<A, C>> right(__<__<__<Star.µ, f>, B>, C> pab) {
      return ei -> ei
        .either(a -> A().pure(Either.left(a)), b -> A().map(Either::right, _FjProF.asStar(pab).f(b)));
    }
  }
}