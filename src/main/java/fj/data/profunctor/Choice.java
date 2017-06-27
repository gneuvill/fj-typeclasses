package fj.data.profunctor;

import fj.F;
import fj._Fj;
import fj.data.Either;
import fj.control.Category;
import fj.data.Functor;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import static fj.Function.identity;
import static fj.data.Either.either_;

public interface Choice<p> extends Profunctor<p> {

  <A, B, C> __2<p, Either<A, C>, Either<B, C>> left(__<__<p, A>, B> pab);

  <A, B, C> __2<p, Either<A, B>, Either<A, C>> right(__<__<p, B>, C> pab);

  default <A, B, C, D> __2<p, Either<A, C>, Either<B, D>> splitChoice(Category<p> C
    , __<__<p, A>, B> l
    , __<__<p, C>, D> r) {
    return C.composeFlipped(left(l), right(r));
  }

  default <A, B, C> __2<p, Either<A, B>, C> fanin(Category<p> C
    , __<__<p, A>, C> l
    , __<__<p, B>, C> r) {
    final __2<p, Either<C, C>, C> join =
      dimap(either_(identity(), identity()), identity(), C.id());

    return C.composeFlipped(splitChoice(C, l, r), join);
  }

  static FChoice f() { return () -> {}; }

  interface FChoice extends Choice<F.µ>, FProfunctor {
    @Override
    default <A, B, C> F<Either<A, C>, Either<B, C>> left(__<__<F.µ, A>, B> pab) {
      return ei -> ei.either(a -> Either.left(_Fj.asF(pab).f(a)), Either::right);
    }

    @Override
    default <A, B, C> F<Either<A, B>, Either<A, C>> right(__<__<F.µ, B>, C> pab) {
      return ei -> Functor.<A>either().map(_Fj.asF(pab), ei);
    }
  }
}
