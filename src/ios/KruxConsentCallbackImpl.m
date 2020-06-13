#import <Foundation/Foundation.h>
#import "KruxConsentCallbackImpl.h"

@implementation KruxConsentCallbackImpl

- (void)handleConsentGetError:(NSString *)consentGetError {
    NSLog(@"Consent Get Error: %@", consentGetError);
}

- (void)handleConsentGetResponse:(NSString *)consentGetResponse {
    NSLog(@"Consent Get Response: %@", consentGetResponse);
}

- (void)handleConsentSetError:(NSString *)consentSetError {
    NSLog(@"Consent Set Error: %@", consentSetError);
}

- (void)handleConsentSetResponse:(NSString *)consentSetResponse {
    NSLog(@"Consent Set Response: %@", consentSetResponse);
}

- (void)handleConsumerPortabilityError:(NSString *)consumerPortabilityError {
    NSLog(@"Consumer Portability Error: %@", consumerPortabilityError);
}

- (void)handleConsumerPortabilityResponse:(NSString *)consumerPortabilityResponse {
    NSLog(@"Consumer Portability Response: %@", consumerPortabilityResponse);
}

- (void)handleConsumerRemoveError:(NSString *)consumerRemoveError {
    NSLog(@"Consumer Remove Error: %@", consumerRemoveError);
}

- (void)handleConsumerRemoveResponse:(NSString *)consumerRemoveResponse {
    NSLog(@"Consumer Remove Response: %@", consumerRemoveResponse);
}

@end