import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared';
import {
    ReviewComponent,
    ReviewDetailComponent,
    ReviewUpdateComponent,
    ReviewDeletePopupComponent,
    ReviewDeleteDialogComponent,
    reviewRoute,
    reviewPopupRoute
} from './';

const ENTITY_STATES = [...reviewRoute, ...reviewPopupRoute];

@NgModule({
    imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReviewComponent, ReviewDetailComponent, ReviewUpdateComponent, ReviewDeleteDialogComponent, ReviewDeletePopupComponent],
    entryComponents: [ReviewComponent, ReviewUpdateComponent, ReviewDeleteDialogComponent, ReviewDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestReviewModule {}
